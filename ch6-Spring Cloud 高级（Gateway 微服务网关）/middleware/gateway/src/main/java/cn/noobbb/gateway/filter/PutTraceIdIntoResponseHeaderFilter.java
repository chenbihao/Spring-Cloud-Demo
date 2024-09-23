package cn.noobbb.gateway.filter;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.apache.skywalking.apm.toolkit.webflux.WebFluxSkyWalkingOperators;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PutTraceIdIntoResponseHeaderFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String traceId = WebFluxSkyWalkingOperators.continueTracing(exchange, TraceContext::traceId);
//        exchange.getResponse().getHeaders().set("x-trace-id", traceId);
        return chain.filter(exchange);
    }
}