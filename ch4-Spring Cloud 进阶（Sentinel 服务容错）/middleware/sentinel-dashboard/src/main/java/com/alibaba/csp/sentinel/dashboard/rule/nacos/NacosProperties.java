package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "nacos")
public class NacosProperties {

    /**
     * nacos 服务端地址
     */
    private String serverAddr = "localhost:8848";
    /**
     * nacos 服务端用户名 服务端未开启用户名密码验证可以留空或者不配置
     */
    private String username;
    /**
     * nacos 服务端密码 服务端未开启用户名密码验证可以留空或者不配置
     */
    private String password;
    /**
     * sentinel 配置所在的命名空间
     */
    private String namespace = "dev";
}