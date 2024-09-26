package cn.noobbb.coupon.customer;

import feign.Logger;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

// Configuration 注解声明配置类
// 类中定义的 Bean 方法会被 AnnotationConfigApplicationContext 和 AnnotationConfigWebApplicationContext 扫描并初始化
@org.springframework.context.annotation.Configuration
public class Configuration {

    /**
     * 注册Bean并添加负载均衡功能
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder register() {
        return WebClient.builder();
    }

    /**
     * OpenFeign 组件自定义的一种日志级别，用来控制 OpenFeign 组件向日志中写入什么内容
     *
     * - NONE：不记录任何信息，这是 OpenFeign 默认的日志级别
     * - BASIC：只记录服务请求的 URL、HTTP Method、响应状态码（如 200、404 等）和服务调用的执行时间
     * - HEADERS：在 BASIC 的基础上，还记录了请求和响应中的 HTTP Headers
     * - FULL：在 HEADERS 级别的基础上，还记录了服务请求和服务响应中的 Body 和 metadata
     */
    @Bean
    Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }
}