package cn.noobbb.coupon.template.api.beans;

import cn.noobbb.coupon.template.api.beans.rules.TemplateRule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * 优惠券模板 VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponTemplateInfo {

    /**
     * id
     *  tips:尽可能减少级联配置，用单表查询取而代之。
     *  过深的级联层级所带来的 DB 层压力可能会在洪峰流量下被急剧放大，而 DB 恰恰是最不抗压的一环。
     */
    private Long id;

    @NotNull
    private String name;

    /**
     * 优惠券描述
     */
    @NotNull
    private String desc;

    /**
     * 优惠券类型
     */
    @NotNull
    private String type;

    /**
     * 适用门店 - 若无则为全店通用券
     */
    private Long shopId;

    /**
     * 优惠券规则
     */
    @NotNull
    private TemplateRule rule;

    /**
     * 是否可用
     */
    private Boolean available;
}
