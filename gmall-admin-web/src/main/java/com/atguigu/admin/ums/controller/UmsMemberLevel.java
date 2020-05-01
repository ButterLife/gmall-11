package com.atguigu.admin.ums.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.to.CommonResult;
import com.atguigu.gmall.ums.entity.MemberLevel;
import com.atguigu.gmall.ums.service.MemberLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@CrossOrigin
@RestController
public class UmsMemberLevel {
    @Reference
    private MemberLevelService memberLevelService;

    //查询所有会员的所有信息
    @GetMapping("/memberLevel/list")
    public Object memberLevelList() {

        //获取到会员表中所有的数据
        List<MemberLevel> emberLevelmList = memberLevelService.list();
        log.debug("获取到了会员列表");
        return new CommonResult().success(emberLevelmList);
    }

}
