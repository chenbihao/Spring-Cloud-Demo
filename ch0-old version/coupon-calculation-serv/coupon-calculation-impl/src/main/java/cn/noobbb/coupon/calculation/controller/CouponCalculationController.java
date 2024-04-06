package cn.noobbb.coupon.calculation.controller;

import cn.noobbb.coupon.calculation.api.beans.ShoppingCart;
import cn.noobbb.coupon.calculation.api.beans.SimulationOrder;
import cn.noobbb.coupon.calculation.api.beans.SimulationResponse;
import cn.noobbb.coupon.calculation.service.intf.CouponCalculationService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("calculator")
public class CouponCalculationController {

    @Autowired
    private Gson gson;

    @Autowired
    private CouponCalculationService calculationService;

    /**
     * 优惠券结算
     */
    @PostMapping("/checkout")
    @ResponseBody
    public ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart settlement) {
        log.info("do calculation: {}", gson.toJson(settlement));
        return calculationService.calculateOrderPrice(settlement);
    }

    /**
     * 优惠券列表挨个试算
     * 给客户提示每个可用券的优惠额度，帮助挑选
     */
    @PostMapping("/simulate")
    @ResponseBody
    public SimulationResponse simulate(@RequestBody SimulationOrder simulator) {
        log.info("do simulation: {}", gson.toJson(simulator));
        return calculationService.simulateOrder(simulator);
    }
}
