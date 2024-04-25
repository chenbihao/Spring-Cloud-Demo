package cn.noobbb.coupon.customer;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

// Configuration 注解声明配置类
// 类中定义的 Bean 方法会被 AnnotationConfigApplicationContext 和 AnnotationConfigWebApplicationContext 扫描并初始化
@org.springframework.context.annotation.Configuration
public class Configuration {

    // 注册Bean并添加负载均衡功能
    @Bean
    @LoadBalanced
    public WebClient.Builder register() {
        return WebClient.builder();
    }
}