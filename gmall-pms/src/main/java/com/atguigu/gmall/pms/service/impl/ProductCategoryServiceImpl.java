package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.constant.SysCacheConstant;
import com.atguigu.gmall.pms.entity.PmsProdectCategoryWithChildrentItern;
import com.atguigu.gmall.pms.entity.ProductCategory;
import com.atguigu.gmall.pms.mapper.ProductCategoryMapper;
import com.atguigu.gmall.pms.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.reflections.Reflections.log;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
@Service
@Component
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    //注入mapper的方法
    @Autowired
    ProductCategoryMapper productCategoryMapper;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<PmsProdectCategoryWithChildrentItern> getCatelogWithChilder(Integer i) {
        List<PmsProdectCategoryWithChildrentItern> items;
        Object cacheMenu = redisTemplate.opsForValue().get(SysCacheConstant.SYS_MENUN);
        if (cacheMenu != null) {
            log.debug("命中缓存数据。。。。");
            items = (List<PmsProdectCategoryWithChildrentItern>)cacheMenu;
        } else {
            items = productCategoryMapper.getListCategoryChilren(i);
            //将查询到的结果保存到缓存中去
            redisTemplate.opsForValue().set(SysCacheConstant.SYS_MENUN, items);
        }
        return items;
    }
}
