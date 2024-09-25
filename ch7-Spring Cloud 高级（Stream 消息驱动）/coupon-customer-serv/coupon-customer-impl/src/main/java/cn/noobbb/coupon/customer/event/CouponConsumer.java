package cn.noobbb.coupon.customer.event;

import cn.noobbb.coupon.customer.api.beans.RequestCoupon;
import cn.noobbb.coupon.customer.service.intf.CouponCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
public class CouponConsumer {

    @Autowired
    private CouponCustomerService customerService;

    @Bean
    public Consumer<RequestCoupon> addCoupon() {
        return request -> {
            log.info("received: {}", request);
            customerService.requestCoupon(request);
        };
    }

    @Bean
    public Consumer<String> deleteCoupon() {
        return request -> {
            log.info("received: {}", request);
            List<Long> params = Arrays.stream(request.split(","))
                    .map(Long::valueOf)
                    .toList();
            customerService.deleteCoupon(params.get(0), params.get(1));
        };
    }

    //@ServiceActivator(inputChannel = "errorChannel")
//    @ServiceActivator(inputChannel = "request-coupon-topic.add-coupon-group.errors")
//    public void requestCouponFallback(ErrorMessage errorMessage) throws Exception {
//        log.info("consumer error fallback: {}", errorMessage);
//        // 实现自己的逻辑，例如告警通知
//        // 告警完后可以考虑是丢弃信息，还是发送到某个队列里等错误处理完后再把消息重新投递回原来的队列里
//
//    }

    @Bean
    public Consumer<ErrorMessage> requestCouponErrorHandler() {
        return v -> {
            log.info("consumer error handler: {}", v);
            // 实现自己的逻辑，例如告警通知
            // 告警完后可以考虑是丢弃信息，还是发送到某个队列里等错误处理完后再把消息重新投递回原来的队列里

//            // 这里抛出异常则转入死信队列
//            throw new IllegalArgumentException("抛出异常转入死信队列");
        };
    }

    @Bean
    public Consumer<RequestCoupon> addCouponDelay() {
        return request -> {
            log.info("received: {}", request);
            customerService.requestCoupon(request);
        };
    }

}

