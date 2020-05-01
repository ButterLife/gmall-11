package com.atguigu.admin.oms.controller;


import com.atguigu.admin.oms.component.OssCompent;
import com.atguigu.gmall.to.CommonResult;
import com.atguigu.gmall.to.OssPolicyResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Oss相关操作接口
 * 1.阿里云上传
 *      前端页面form表单文档上传===》后台（收到文件流）---》ossClient.upload到阿里云
 *
 *      2.如果配置成自己阿里云
 *          1.前端项目里面搜索leifengyang改成自己的阿里云服务端地址
 *          2.application.properties上 aliyun.oss.endpoint=ooshopefulgmall.oss-cn-hangzhou.aliyuncs.com 改成自己的
 *          3.在阿里云的对象服务里面的设置，将阿里云的跨域设置成都可以访问的请求格式
 *
 */
@CrossOrigin
@Controller
@Api(tags = "OssController",description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
	@Autowired
	private OssCompent ossCompent;

	@ApiOperation(value = "oss上传签名生成")
	@GetMapping(value = "/policy")
	@ResponseBody
	public Object policy() {
		OssPolicyResult result = ossCompent.policy();
		return new CommonResult().success(result);
	}

}
