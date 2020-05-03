package com.atguigu.gmall.to.es;

import lombok.Data;

import java.io.Serializable;

/**
 * 为es提取不同的数据进行的分类提取
 */
@Data
public class EsProductAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long productAttributeId;
    //属性值
    private String value;//3G
    //属性参数：0->规格；1->参数
    private Integer type;//规格，销售属性；参数，筛选参数
    // private Integer searchType;
    //属性名称
    private String name;//网络制式

    private Long productId;

}