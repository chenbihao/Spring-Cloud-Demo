package cn.noobbb.coupon.template.controller;

import cn.noobbb.coupon.template.api.beans.CouponTemplateInfo;
import cn.noobbb.coupon.template.api.beans.PagedCouponTemplateInfo;
import cn.noobbb.coupon.template.api.beans.TemplateSearchParams;
import cn.noobbb.coupon.template.service.intf.CouponTemplateService;
import cn.noobbb.coupon.template.service.intf.CouponTemplateServiceTCC;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/template")
public class CouponTemplateController {

    @Autowired
    private Gson gson;

    @Autowired
    private CouponTemplateServiceTCC couponTemplateService;

    /**
     * 创建优惠券
     */
    @PostMapping("/addTemplate")
    public CouponTemplateInfo addTemplate(@Valid @RequestBody CouponTemplateInfo request) {
        log.info("Create coupon template: data={}", request);
        return couponTemplateService.createTemplate(request);
    }

    /**
     * 克隆优惠券
     */
    @PostMapping("/cloneTemplate")
    public CouponTemplateInfo cloneTemplate(@RequestParam("id") Long templateId) {
        log.info("Clone coupon template: data={}", templateId);
        return couponTemplateService.cloneTemplate(templateId);
    }

    /**
     * 读取优惠券
     */
    @GetMapping("/getTemplate")
    @SentinelResource(value = "getTemplate")
    public CouponTemplateInfo getTemplate(@RequestParam("id") Long id) {
        log.info("Load template, id={}", id);
        return couponTemplateService.loadTemplateInfo(id);
    }

    /**
     * 批量获取
     */
    @GetMapping("/getBatch")
    @SentinelResource(value = "getTemplateInBatch",
            fallback = "getTemplateInBatch_fallback",
            blockHandler = "getTemplateInBatch_block")
    public Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids) {
        // 如果接口被熔断，那么下面这行log不会被打印出来
        log.info("getTemplateInBatch: {}", gson.toJson(ids));

//        // 可以测试异常比例、异常数熔断
//        if (ids.size() == 3) {
//            throw new RuntimeException("异常");
//        }
        // 可以测试慢调用熔断
//        try {
//            Thread.sleep(500 * ids.size());
//        } catch (Exception e) {
//        }

        return couponTemplateService.getTemplateInfoMap(ids);
    }

    public Map<Long, CouponTemplateInfo> getTemplateInBatch_block(Collection<Long> ids, BlockException exception) {
        log.info("接口被限流");
        throw new IllegalArgumentException("接口被限流");
    }

    public Map<Long, CouponTemplateInfo> getTemplateInBatch_fallback(Collection<Long> ids) {
        log.info("接口被降级");
        throw new IllegalArgumentException("接口被降级");
    }

    /**
     * 搜索模板
     */
    @PostMapping("/search")
    public PagedCouponTemplateInfo search(@Valid @RequestBody TemplateSearchParams request) {
        log.info("search templates, payload={}", request);
        return couponTemplateService.search(request);
    }

    /**
     * 优惠券无效化
     */
    @DeleteMapping("/deleteTemplate")
    public void deleteTemplate(@RequestParam("id") Long id) {
        log.info("delete template, id={}", id);
        couponTemplateService.deleteTemplate(id);
    }

    /**
     * 优惠券无效化 TCC
     */
    @DeleteMapping("/deleteTemplateTCC")
    @GlobalTransactional(name = "coupon-template-serv", rollbackFor = Exception.class)
    public void deleteTemplateTCC(@RequestParam("id") Long id) {
        log.info("delete template TCC, id={}", id);
        couponTemplateService.deleteTemplateTCC(null,id);
    }
}
