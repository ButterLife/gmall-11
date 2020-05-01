package com.atguigu.admin.aop;

import com.atguigu.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

/**
 * 统一的数据处理，给前端返回500的json
 *
 * @ControllerAdvice 系统捕捉错误注解
 */
@Slf4j
@RestControllerAdvice
public class GlobExceptionHandler {

    //处理数学异常
    @ExceptionHandler(value = {ArithmeticException.class})
    public Object handLerException(Exception exception) {
        log.debug("系统全局异常感知：", exception.getStackTrace());
        return new CommonResult().validateFailed("数学没有学好。。。");
    }
    @ExceptionHandler(value = {FileNotFoundException.class})
    public Object handLerException02(Exception exception) {
        log.debug("系统全局异常感知文件：", exception.getStackTrace());
        return new CommonResult().validateFailed("没有该文件。。。");
    }
}
