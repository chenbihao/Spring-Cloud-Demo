/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Configuration
@EnableConfigurationProperties(NacosProperties.class)
public class NacosConfig {

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public ConfigService nacosConfigService(NacosProperties nacosProperties) throws Exception {

        String serverAddr = nacosProperties.getServerAddr();
        String namespace = nacosProperties.getNamespace();
        String username = nacosProperties.getUsername();
        String password = nacosProperties.getPassword();

        System.out.println("初始化 Nacos 配置：" + serverAddr + " " + namespace  +" Username:"+ username);

        // 将Nacos的注册地址引入进来，这里也可以改成通过配置文件来注入
        Properties properties = new Properties();
        // 设置nacos服务地址
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        // 设置nacos命名空间
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        // 如果有权限校验
        if (StringUtil.isNotBlank(username)) {
            properties.put(PropertyKeyConst.USERNAME, username);
            properties.put(PropertyKeyConst.PASSWORD, password);
        }

        //  return ConfigFactory.createConfigService("localhost");
        return ConfigFactory.createConfigService(properties);
    }
}
