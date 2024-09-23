package cn.noobbb.gateway.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

// GatewayService 它的作用是将变化后的路由信息添加到网关上下文中
@Slf4j
@Service
public class GatewayService {

    // Gateway 内置的路由编辑类
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private ApplicationEventPublisher publisher;

    // RouteDefinition 是 Gateway 网关组件用来封装路由规则的标准类，
    // 里面包含了谓词、过滤器和 metadata 等一系列构造路由规则所需要的元素
    public void updateRoutes(List<RouteDefinition> routes) {
        if (CollectionUtils.isEmpty(routes)) {
            log.info("No routes found");
            return;
        }
        routes.forEach(r -> {
            try {
                // 将路由规则写入上下文
                routeDefinitionWriter.save(Mono.just(r)).subscribe();
            } catch (Exception e) {
                log.error("cannot update route, id={}", r.getId());
            }
        });
        // 发布一个路由刷新事件
        // publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}