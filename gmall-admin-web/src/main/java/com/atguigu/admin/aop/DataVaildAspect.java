package com.atguigu.admin.aop;

import com.atguigu.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.io.FileInputStream;

/**
 * 数据校验切面
 * 利用aop完成统一的数据校验，有数据错误就返回错误信息
 * 1.导入切面场景
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-aop</artifactId>
 * </dependency>
 * 2.编写切面
 * 1.@Aspect
 * 2.切入点表达式
 * 3.通知
 * 前置通知    方法执行之前通知
 * 后置通知    方法之后触发
 * 异常通知    方法出现异常通知
 * 环绕通知
 * 4合1
 */
@Slf4j
@Aspect
@Component
public class DataVaildAspect {
    @Around("execution(* com.atguigu.admin..*Controller.*(..))")
    public Object vailAround(ProceedingJoinPoint point) {
        Object proceed = null;
        BindingResult result = null;
            try {
            log.debug("校验切面介入工作");
            //获取到目标方法的参数值
            Object[] args = point.getArgs();
            for (Object obj : args) {
                if (obj instanceof BindingResult) {
                    result = (BindingResult) obj;
                    if (result.getErrorCount() > 0) {
                        return new CommonResult().validateFailed(result);
                    }
                }
            }
            //反射得到的目标执行的方法
            proceed = point.proceed(args);
        } catch (Throwable throwable) {
            //抛出异常，给全局异常处理
            throw new RuntimeException(throwable);
        } finally {

        }

        return proceed;

    }
}
