package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.Brand;
import com.atguigu.gmall.pms.mapper.BrandMapper;
import com.atguigu.gmall.pms.service.BrandService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
@Service
@Component
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    /**
     * 查询分页表的数据
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfoVo brandPageInf(String keyword, Integer pageNum, Integer pageSize) {
        //根据keyword类模糊查询品牌数据
        QueryWrapper<Brand> wrapperKeyword = null;
        if (!StringUtils.isEmpty(keyword)) {
            wrapperKeyword = new QueryWrapper<Brand>().like("name", keyword);
        }
        IPage<Brand> brandIPage = brandMapper.selectPage(new Page<Brand>(pageNum.longValue(), pageSize.longValue()), wrapperKeyword);
        //封装数据 返回数据给前端
        PageInfoVo pageInfoVo = new PageInfoVo(brandIPage.getTotal(), brandIPage.getTotal(), pageSize.longValue(), brandIPage.getRecords(), brandIPage.getCurrent());
        return pageInfoVo;
    }
}
