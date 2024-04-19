package cn.noobbb.coupon.calculation.service.intf;

import cn.noobbb.coupon.calculation.api.beans.ShoppingCart;
import cn.noobbb.coupon.calculation.api.beans.SimulationOrder;
import cn.noobbb.coupon.calculation.api.beans.SimulationResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface CouponCalculationService {

    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart);

    SimulationResponse simulateOrder(@RequestBody SimulationOrder cart);
}
