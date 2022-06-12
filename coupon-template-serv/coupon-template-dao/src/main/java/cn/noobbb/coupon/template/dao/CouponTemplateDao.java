package cn.noobbb.coupon.template.dao;

import cn.noobbb.coupon.template.dao.entity.CouponTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * CouponTemplateDao
 */
public interface CouponTemplateDao extends JpaRepository<CouponTemplate, Long> {

    /**
     * 根据Shop ID查询出所有券模板                JPA 约定大于配置：<起手式>By<查询字段><连接词>
     *
     * @param shopId shopId
     * @return
     */
    List<CouponTemplate> findAllByShopId(Long shopId);

    /**
     * IN查询 + 分页支持的语法
     *
     * @param Id
     * @param page
     * @return
     */
    Page<CouponTemplate> findAllByIdIn(List<Long> Id, Pageable page);

    /**
     * 根据shop ID + 可用状态查询店铺有多少券模板
     *
     * @param shopId
     * @param available
     * @return
     */
    Integer countByShopIdAndAvailable(Long shopId, Boolean available);

    /**
     * 将优惠券设置为不可用                JPA 自定义SQL： @Query
     *
     * @param id
     * @return
     */
    @Modifying
    @Query("update CouponTemplate c set c.available = 0 where c.id = :id")
    int makeCouponUnavailable(@Param("id") Long id);



}
