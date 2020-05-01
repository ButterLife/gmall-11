package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.PmsProdectCategoryWithChildrentItern;
import com.atguigu.gmall.pms.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
public interface ProductCategoryService extends IService<ProductCategory> {


    List<PmsProdectCategoryWithChildrentItern> getCatelogWithChilder(Integer i);
}
