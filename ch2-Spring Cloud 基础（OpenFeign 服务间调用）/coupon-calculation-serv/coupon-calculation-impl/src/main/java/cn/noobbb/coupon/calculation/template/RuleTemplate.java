package cn.noobbb.coupon.calculation.template;


import cn.noobbb.coupon.calculation.api.beans.ShoppingCart;

public interface RuleTemplate {

    /**
     * 计算优惠券
     */
    ShoppingCart calculate(ShoppingCart settlement);
}
