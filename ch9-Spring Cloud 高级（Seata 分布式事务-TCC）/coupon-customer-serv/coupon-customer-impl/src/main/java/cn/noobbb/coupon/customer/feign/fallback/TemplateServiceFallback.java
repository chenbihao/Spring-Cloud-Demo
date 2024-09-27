package cn.noobbb.coupon.customer.feign.fallback;

import cn.noobbb.coupon.customer.feign.TemplateService;
import cn.noobbb.coupon.template.api.beans.CouponTemplateInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class TemplateServiceFallback implements TemplateService {

    @Override
    public CouponTemplateInfo getTemplate(Long id) {
        log.error("fallback getTemplate");
        return null;
    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInBatch(Collection<Long> ids) {
        log.error("fallback getTemplateInBatch");
        return Maps.newHashMap();
    }

    @Override
    public void deleteTemplate(Long id) {
        log.error("fallback deleteTemplate");
    }

}