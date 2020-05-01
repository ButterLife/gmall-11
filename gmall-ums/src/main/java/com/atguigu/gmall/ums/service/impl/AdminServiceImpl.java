package com.atguigu.gmall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.ums.entity.Admin;
import com.atguigu.gmall.ums.mapper.AdminMapper;
import com.atguigu.gmall.ums.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2020-04-24
 */
@Service
@Component
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    //要注入dao层的接口，实现与数据的交互
    @Autowired
    AdminMapper adminMappera;

    /**
     * 实现自己的登入方法
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Admin login(String username, String password) {
        //使用spring自带的md5的加密方式
        String passwordMD5 = DigestUtils.md5DigestAsHex(password.getBytes());
        //使用mybatisplus的查询方法来获取用户登入的方式是否正确
        QueryWrapper<Admin> eq
                = new QueryWrapper<Admin>().eq("username", username).eq("password", passwordMD5);
        Admin admin1 = adminMappera.selectOne(eq);
        return admin1;
    }

    /**
     * 自定义获取用的详情
     *
     * @param userName
     * @return
     */
    @Override
    public Admin getUserInfo(String userName) {
        System.out.println(userName);

        return adminMappera.selectOne(new QueryWrapper<Admin>().eq("userName", userName));
    }
}