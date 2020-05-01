package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.mapper.*;
import com.atguigu.gmall.pms.service.ProductService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductParam;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
@Slf4j
@Service
@Component
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductAttributeValueMapper productAttributeValueMapper;
    @Autowired
    ProductLadderMapper productLadderMapper;
    @Autowired
    ProductFullReductionMapper productFullReductionMapper;
    @Autowired
    SkuStockMapper skuStockMapper;
    //使用当前线程共享同样的数据
    ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 自定义实现查询商品列表
     *
     * @param param
     * @return
     */
    @Override
    public PageInfoVo getProducer(PmsProductQueryParam param) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        //封装数据查询数据
        //商品名称
        if (null != param.getKeyword()) {
            wrapper.like("name", param.getKeyword());
        }
        //品牌编号
        if (null != param.getBrandId()) {
            wrapper.eq("brand_id ", param.getBrandId());
        }
        //审核状态
        if (param.getVerifyStatus() != null) {
            wrapper.eq("verify_status ", param.getVerifyStatus());
        }
        //商品分类编号
        if (param.getProductCategoryId() != null) {
            wrapper.eq("product_category_id ", param.getProductCategoryId());
        }
        //商品货号
        if (!StringUtils.isEmpty(param.getProductSn())) {
            wrapper.eq("product_sn", param.getProductSn());
        }
        //上架状态
        if (param.getPublishStatus() != null) {
            wrapper.eq("publish_status", param.getPublishStatus());
        }

        //查询出分页的数据，这里还没有带条件查询
        IPage<Product> page = productMapper.selectPage(new Page<Product>(param.getPageNum(), param.getPageSize()), wrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(page.getTotal(), page.getPages(), param.getPageSize(), page.getRecords(), page.getCurrent());
        return pageInfoVo;
    }

    /**
     * 保存商品数据（大保存）
     * <p>
     * <p>
     * 保存大保存，必须是要有事务的
     * 1.考虑哪些是有必要回滚的，有些无关紧要的数据没必要回滚
     * 使用事务的传播行为：
     * 使用
     * 2.事务的传播行为
     * Propagation propagation() default Propagation.REQUIRED;
     * REQUIRED:（必须）如果当前有事务，就和之前的事务公用一个事务，没有就自己创建一个
     * REQUIRES_NEW:（必须）
     * Create a new transaction, and suspend the current transaction if one exists.
     * 创建一个新的事务，如果以前有事务，暂停前面的事务，执行完当前的事务在执行以前的
     * SUPPORTS:（支持）
     * upport a current transaction, execute non-transactionally if none exists.
     * 之前有事务，就以事务运行，没有事务也可以运行
     * MANDATORY:（强制）
     * Support a current transaction, throw an exception if none exists.
     * 一定要有事务，如果没有事务就报错
     * NOT_SUPPORTED:（不支持）
     * Execute non-transactionally, suspend the current transaction if one exists.
     * 不支持在事务运行，如果有事务了，就挂起当前存在的事务
     * NEVER:（从不）
     * Execute non-transactionally, throw an exception if a transaction exists.
     * 不支持在事务中运行，如果有事务，就抛出异常
     * NESTED:（嵌套）
     * Execute within a nested transaction if a current transaction exists,
     * 开启一个子事务（mysql不支持）
     *   事务底层是使用aop做的，
     *        aop是动态代理
     *            所以在本类中调用事务的方法没有经过增强的，所以还是一个事务支持
     *            将方法放置到其他类中是是经过代理对象的，所以使用是可以支持事务的
     *
     *事务的问题
     *       service自己调用自己的方法，无法加上真正的自己内部调整给个事务
     *       解决：
     *
     * @param productParam
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveProdect(PmsProductParam productParam) {
        //通过代理对象来调用本类的方法实现事务
        ProductServiceImpl proxy = (ProductServiceImpl) AopContext.currentProxy();
        //1. `pms_product` 保存商品的基本信息
        proxy.saveBaseInfo(productParam);
        //2. pms_product_attribute_value` 保存商品的属性表
        proxy.saveProductAttribute(productParam);
        /**
         * 一下都可以异常捕获
         */
        //3.`pms_product_ladder`保存商品阶梯数据
        proxy.saveProductLadder(productParam);
        //4.`pms_product_full_reduction` 满减表
        proxy.saveProductFull(productParam);
        //5.`pms_sku_stock` 保存商品的sku与库存表
        proxy.saveSkuStock(productParam);
    }

    /**
     * 保存商品的sku数据
     *
     * @param productParam
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSkuStock(PmsProductParam productParam) {
        List<SkuStock> skuStockList = productParam.getSkuStockList();

        for (int i = 0; i < skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i);
            skuStock.setProductId(threadLocal.get());
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                //生成sku的规则，id+sku自增id+时间
                skuStock.setSkuCode(threadLocal.get() + "_" + (i + 1));

            }

            //向数据库中插入数据
            skuStockMapper.insert(skuStock);
        }
    }

    /**
     * 保存商品满减数据
     *
     * @param productParam
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductFull(PmsProductParam productParam) {
        List<ProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        productFullReductionList.forEach((fullReduction) -> {
            //设置商品id号
            fullReduction.setProductId(threadLocal.get());
            productFullReductionMapper.insert(fullReduction);
        });
    }

    /**
     * 保存商品的阶梯数据
     *
     * @param productParam
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductLadder(PmsProductParam productParam) {
        List<ProductLadder> productLadderList = productParam.getProductLadderList();
        productLadderList.forEach((ladderList) -> {
            //设置商品id号
            ladderList.setProductId(threadLocal.get());
            productLadderMapper.insert(ladderList);
        });
    }

    /**
     * 保存商品的属性
     *
     * @param productParam
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveProductAttribute(PmsProductParam productParam) {
        List<ProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        productAttributeValueList.forEach((productAttribute) -> {
            //获取到商品的自增id
            productAttribute.setProductId(threadLocal.get());
            productAttributeValueMapper.insert(productAttribute);
        });
    }

    /**
     * 保存商品的基本信息
     *
     * @param productParam
     * @Transactional 添加事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveBaseInfo(PmsProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);
        //设置共享变量来为其他方法提供商品id
        threadLocal.set(product.getId());
        productMapper.insert(product);
        log.debug("商品id：{}", product.getId());
        log.debug("商品信息：{}", product);
    }

}
