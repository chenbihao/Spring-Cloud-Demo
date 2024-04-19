package cn.noobbb.coupon.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication()  // 自动开启包路径扫描，并启动一系列的自动装配流程（AutoConfig）
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan(basePackages = {"cn.noobbb"})
@EnableJpaRepositories(basePackages = {"cn.noobbb"}) //用于扫描Dao @Repository
@EntityScan(basePackages = {"cn.noobbb"}) //用于扫描JPA实体类 @Entity，默认扫本包当下路径
public class CustomerApplication {

    // 如果 Application 在 cn.noobbb.customer 下，
    // 而项目中又需要加载来自 cn.noobbb.template 下的类资源，就必须额外声明扫包路径，
    // 否则只有在 cn.noobbb.customer 和其子路径之下的资源才会被加载。

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}