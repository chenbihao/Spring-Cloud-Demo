package cn.noobbb.gateway.dynamic;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

// 加载场景实现类
// InitializingBean 是 Spring 框架提供的标准接口
// 作用是在当前类所有的属性加载完成后，执行一段定义在 afterPropertiesSet 方法中的自定义逻辑
@Slf4j
@Configuration
public class DynamicRoutesLoader implements InitializingBean {

    @Autowired
    private NacosConfigManager configManager;
    @Autowired
    private NacosConfigProperties configProps;
    @Autowired
    private DynamicRoutesListener dynamicRoutesListener;

    private static final String ROUTES_CONFIG = "routes-config.json";

    @Override
    public void afterPropertiesSet() throws Exception {

        // 调用 Nacos 提供的 NacosConfigManager 类加载指定的路由配置文件
        String routes = configManager.getConfigService().getConfig(ROUTES_CONFIG
                , configProps.getGroup(), 10000);

        dynamicRoutesListener.receiveConfigInfo(routes);

        // 将前面我们定义的 DynamicRoutesListener 注册到 "routes-config.json" 监听列表中
        // 每次这个文件发生变动，监听器都能够获取到通知
        configManager.getConfigService().addListener(ROUTES_CONFIG
                , configProps.getGroup()
                , dynamicRoutesListener);
    }

}