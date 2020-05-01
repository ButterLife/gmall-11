package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.ProductAttribute;
import com.atguigu.gmall.pms.mapper.ProductAttributeMapper;
import com.atguigu.gmall.pms.service.ProductAttributeService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
@Service
@Component
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements ProductAttributeService {
    @Autowired
    ProductAttributeMapper productAttributeMapper;

    /**
     * 获取到分类属性下的销售属性和基本属性
     *
     * @param cid
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoVo getProductAttributeList(Long cid, Integer type, Integer pageNum, Integer pageSize) {
        //增加条件查询
        QueryWrapper<ProductAttribute> queryWrapperProductAttribute = new QueryWrapper<>();
        queryWrapperProductAttribute.eq("product_attribute_category_id", cid);
        queryWrapperProductAttribute.eq("type", type);
        IPage<ProductAttribute> productAttributeIPage = productAttributeMapper.selectPage(new Page<ProductAttribute>(pageNum, pageSize), queryWrapperProductAttribute);
        //封装数据返回
        PageInfoVo vo = PageInfoVo.getVo(productAttributeIPage, pageSize);
        return vo;
    }
}
