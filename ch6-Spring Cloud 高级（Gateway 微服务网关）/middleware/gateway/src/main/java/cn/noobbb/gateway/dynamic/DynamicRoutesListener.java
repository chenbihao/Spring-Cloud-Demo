package cn.noobbb.gateway.dynamic;

import com.alibaba.nacos.api.config.listener.Listener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

// 当被监听的 Nacos 配置文件发生变化的时候，
// Nacos 框架会自动调用 receiveConfigInfo 方法执行自定义逻辑
@Slf4j
@Component
public class DynamicRoutesListener implements Listener {

    @Autowired
    private Gson gson;
    @Autowired
    private GatewayService gatewayService;

    @Override
    public Executor getExecutor() {
        log.info("getExecutor");
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        log.info("received routes changes {}", configInfo);

        // Nacos Config 中的配置项需要符合 RouteDefinition 格式
        Type listType = new TypeToken<ArrayList<RouteDefinition>>(){}.getType();
        List<RouteDefinition> definitionList = gson.fromJson(configInfo, listType);
        gatewayService.updateRoutes(definitionList);
    }
}