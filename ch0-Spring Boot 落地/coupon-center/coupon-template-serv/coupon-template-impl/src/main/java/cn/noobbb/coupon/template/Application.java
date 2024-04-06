package cn.noobbb.coupon.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 启动类
 */
@SpringBootApplication // 自动开启包路径扫描，并启动一系列的自动装配流程（AutoConfig）
@EnableJpaAuditing
@ComponentScan(basePackages = {"cn.noobbb"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
