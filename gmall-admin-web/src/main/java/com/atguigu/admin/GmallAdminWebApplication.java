package com.atguigu.admin;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 排除数据驱动包自动导入：exclude = {DataSourceAutoConfiguration.class}
 * 如果导入的依赖，引入一个自动配置的场景
 *   1.这个场景自动会会默认生效，我们就必须配置他
     2.不想配置
        1.引入的时候排除场景依赖
        2.排除场景自动配置类
 */
@EnableDubbo
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GmallAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallAdminWebApplication.class, args);
    }

}
