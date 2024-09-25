package cn.noobbb.coupon.customer.controller;


import cn.noobbb.coupon.calculation.api.beans.ShoppingCart;
import cn.noobbb.coupon.calculation.api.beans.SimulationOrder;
import cn.noobbb.coupon.calculation.api.beans.SimulationResponse;
import cn.noobbb.coupon.customer.api.beans.RequestCoupon;
import cn.noobbb.coupon.customer.api.beans.SearchCoupon;
import cn.noobbb.coupon.customer.dao.entity.Coupon;
import cn.noobbb.coupon.customer.event.CouponProducer;
import cn.noobbb.coupon.customer.service.intf.CouponCustomerService;
import cn.noobbb.coupon.template.api.beans.CouponInfo;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("coupon-customer")
@RefreshScope
public class CouponCustomerController {

    @Autowired
    private CouponCustomerService customerService;
    @Autowired
    private CouponProducer couponProducer;

    @Value("${disableCouponRequest:false}")
    private Boolean disableCoupon;

    /**
     * 实现的时候最好封装一个search object类
     */
    @PostMapping("findCoupon")
    @SentinelResource(value = "customer-findCoupon")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        return customerService.findCoupon(request);
    }

    /**
     * 领券接口
     */
    @PostMapping("requestCoupon")
    @SentinelResource(value = "requestCoupon")
    public Coupon requestCoupon(@Valid @RequestBody RequestCoupon request) {
        if (disableCoupon) {
            log.info("暂停领取优惠券");
            return null;
        }
        return customerService.requestCoupon(request);
    }

    /**
     * 用户删除优惠券
     */
    @DeleteMapping("deleteCoupon")
    public void deleteCoupon(@RequestParam("userId") Long userId, @RequestParam("couponId") Long couponId) {
        customerService.deleteCoupon(userId, couponId);
    }


    /**
     * 领券接口 Event
     */
    @PostMapping("requestCouponEvent")
    public void requestCouponEvent(@Valid @RequestBody RequestCoupon request) {
        couponProducer.sendCoupon(request);
    }

    /**
     * 领券接口 DelayEvent
     */
    @PostMapping("requestCouponDelayEvent")
    public void requestCouponDelayedEvent(@Valid @RequestBody RequestCoupon request) {
        couponProducer.sendCouponInDelay(request);
    }

    /**
     * 用户删除优惠券 Event
     */
    @DeleteMapping("deleteCouponEvent")
    public void deleteCouponEvent(@RequestParam("userId") Long userId,
                                  @RequestParam("couponId") Long couponId) {
        couponProducer.deleteCoupon(userId, couponId);
    }

    /**
     * 用户模拟计算每个优惠券的优惠价格
     */
    @PostMapping("simulateOrder")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        return customerService.simulateOrderPrice(order);
    }

    /**
     * ResponseEntity - 指定返回状态码 - 可以作为一个课后思考题
     */
    @PostMapping("placeOrder")
    public ShoppingCart checkout(@Valid @RequestBody ShoppingCart info) {
        return customerService.placeOrder(info);
    }


}
