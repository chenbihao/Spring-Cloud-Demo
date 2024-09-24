package cn.noobbb.coupon.customer.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenfeignSentinelInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 传递给下游服务的“来源标记”
        requestTemplate.header("SentinelSource", "coupon-customer-serv");
    }
}
