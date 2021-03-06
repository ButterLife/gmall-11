package com.atguigu.gmall.to.es;

import com.atguigu.gmall.pms.entity.SkuStock;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class EsSkuProductInfo extends SkuStock implements Serializable {
    private String skuTitle;//sku的特点标题
    /**
     * 每个sku不同的属性以及他的值
     * <p>
     * 颜色：黑色
     * 内存：128
     */
    private List<EsProductAttributeValue> attributeValueList;
}
