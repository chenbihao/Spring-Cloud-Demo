package cn.noobbb.coupon.customer.loadbalance;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

// 注意这里不要写上@Configuration注解，因为不是全局配置
public class CanaryRuleConfiguration {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment
            ,LoadBalancerClientFactory loadBalancerClientFactory) {

        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        // 在 Spring 上下文中声明了一个 CanaryRule 规则
        return new CanaryRule(loadBalancerClientFactory.getLazyProvider(name,ServiceInstanceListSupplier.class), name);
    }

//    @Bean
//    public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
//                                                                   LoadBalancerClientFactory loadBalancerClientFactory){
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new RoundRobinLoadBalancer(loadBalancerClientFactory
//                .getLazyProvider(name, ServiceInstanceListSupplier.class),
//                name);
//    }

}
