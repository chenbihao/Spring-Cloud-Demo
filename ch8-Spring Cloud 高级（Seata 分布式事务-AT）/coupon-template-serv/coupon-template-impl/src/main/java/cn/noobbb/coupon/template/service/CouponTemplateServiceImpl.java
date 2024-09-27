package cn.noobbb.coupon.template.service;

import cn.noobbb.coupon.template.api.beans.CouponTemplateInfo;
import cn.noobbb.coupon.template.api.beans.PagedCouponTemplateInfo;
import cn.noobbb.coupon.template.api.beans.TemplateSearchParams;
import cn.noobbb.coupon.template.api.enums.CouponType;
import cn.noobbb.coupon.template.converter.CouponTemplateConverter;
import cn.noobbb.coupon.template.dao.CouponTemplateDao;
import cn.noobbb.coupon.template.dao.entity.CouponTemplate;
import cn.noobbb.coupon.template.service.intf.CouponTemplateService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 优惠券模板类相关操作
 */
@Slf4j
@Service
public class CouponTemplateServiceImpl implements CouponTemplateService {

    @Autowired
    private CouponTemplateDao templateDao;

    /**
     * 创建优惠券模板
     *
     * @param request
     */
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {

        // 创建优惠券
        CouponTemplate template = CouponTemplate.builder()
                .name(request.getName())
                .description(request.getDesc())
                .category(CouponType.convert(request.getType()))
                .available(true)
                .shopId(request.getShopId())
                .rule(request.getRule())
                .build();

        template = templateDao.save(template);

        return CouponTemplateConverter.convertToTemplateInfo(template);
    }

    /**
     * 克隆优惠券模板
     *
     * @param templateId
     * @return
     */
    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {

        CouponTemplate template = templateDao.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("找不到模板 ID"));

        CouponTemplate newOne = new CouponTemplate();
        BeanUtils.copyProperties(template,newOne);
        newOne.setAvailable(true);
        newOne.setId(null);
        templateDao.save(newOne);

        return CouponTemplateConverter.convertToTemplateInfo(newOne);
    }

    /**
     * 模板查询（分页）
     *
     * @param request
     */
    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        CouponTemplate example = CouponTemplate.builder()
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .name(request.getName())
                .build();

        Pageable page = PageRequest.of(request.getPage(), request.getPageSize());
        Page<CouponTemplate> result = templateDao.findAll(Example.of(example), page);

        List<CouponTemplateInfo> couponTemplateInfos = result.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .toList();

        return PagedCouponTemplateInfo.builder()
                .templates(couponTemplateInfos)
                .page(request.getPage())
                .total(result.getTotalElements())
                .build();
    }

    /**
     * 通过模板ID查询优惠券模板
     *
     * @param id
     */
    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        Optional<CouponTemplate> template = templateDao.findById(id);
        return template.map(CouponTemplateConverter::convertToTemplateInfo).orElse(null);
    }

    /**
     * 让优惠券模板无效
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteTemplate(Long id) {
        int rows = templateDao.makeCouponUnavailable(id);
        if (rows==0) {
            throw new IllegalArgumentException("找不到模板 ID: " + id);
        }
    }

    /**
     * 批量查询
     * Map是模板ID，key是模板详情
     *
     * @param ids
     */
    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        List<CouponTemplate> templates = templateDao.findAllById(ids);

        return templates.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toMap(CouponTemplateInfo::getId, Function.identity()));
    }
}
