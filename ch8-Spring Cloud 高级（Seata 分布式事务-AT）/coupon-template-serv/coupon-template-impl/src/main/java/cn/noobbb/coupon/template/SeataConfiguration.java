package cn.noobbb.coupon.template;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class SeataConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean("dataSource")
    @Primary
    public DataSource dataSourceDelegation(DruidDataSource druidDataSource) {
        // DataSourceProxy 是由 Seata 框架提供的一个数据源代理类
        return new DataSourceProxy(druidDataSource);
    }

}