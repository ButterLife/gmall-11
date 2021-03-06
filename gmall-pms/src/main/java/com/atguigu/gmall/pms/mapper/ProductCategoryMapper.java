package com.atguigu.gmall.pms.mapper;

import com.atguigu.gmall.pms.entity.PmsProdectCategoryWithChildrentItern;
import com.atguigu.gmall.pms.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    List<PmsProdectCategoryWithChildrentItern> getListCategoryChilren(Integer i);
}
