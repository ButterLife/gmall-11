package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductParam;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
public interface ProductService extends IService<Product> {

    /**
     * 自定义根据复杂插叙条件返回分页数据
     *
     * @return
     */
    PageInfoVo getProducer(PmsProductQueryParam productQueryParam);

    void saveProdect(PmsProductParam productParam);
}
