package cn.noobbb.coupon.calculation.api.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    /**
     * 商品的价格
     */
    private long price;
    /**
     * 商品在购物车里的数量（非标品计件单位要允许小数存在，这里简化）
     */
    private Integer count;
    /**
     * 商品销售的门店
     */
    private Long shopId;
}
