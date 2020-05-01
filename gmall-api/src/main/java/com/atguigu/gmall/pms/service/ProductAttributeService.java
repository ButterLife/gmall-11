package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.ProductAttribute;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
public interface ProductAttributeService extends IService<ProductAttribute> {
    /**
     * 根据分类查询属性列表或参数列表
     * @param cid
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfoVo getProductAttributeList(Long cid, Integer type, Integer pageNum, Integer pageSize);
}
