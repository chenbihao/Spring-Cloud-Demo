package cn.noobbb.coupon.template.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 优惠券类型
 * @author cbh
 */
@Getter
@AllArgsConstructor
public enum CouponType {

    /**
     * UNKNOWN tips: 防御性编程，用来应对错误的code
     */
    UNKNOWN("unknown", "0"),
    MONEY_OFF("满减券", "1"),
    DISCOUNT("打折", "2"),
    RANDOM_DISCOUNT("随机减", "3"),
    LONELY_NIGHT_MONEY_OFF("晚间双倍优惠券", "4"),
    ANTI_PUA("PUA加倍奉还券", "5");

    private final String description;

    /**
     * 存在数据库里的最终code
     */
    private final String code;

    public static CouponType convert(String code) {
        return Stream.of(values())
                .filter(couponType -> couponType.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
