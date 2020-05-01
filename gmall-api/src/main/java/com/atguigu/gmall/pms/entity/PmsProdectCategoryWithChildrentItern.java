package com.atguigu.gmall.pms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 扩展的分类bean的数据查询bean
 */
@Data
public class PmsProdectCategoryWithChildrentItern extends ProductCategory implements Serializable {
    //封装分类子集合的集合
    private List<ProductCategory> children;
}
