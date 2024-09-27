package cn.noobbb.coupon.template.service;

import cn.noobbb.coupon.template.api.beans.CouponTemplateInfo;
import cn.noobbb.coupon.template.api.beans.PagedCouponTemplateInfo;
import cn.noobbb.coupon.template.api.beans.TemplateSearchParams;
import cn.noobbb.coupon.template.api.enums.CouponType;
import cn.noobbb.coupon.template.converter.CouponTemplateConverter;
import cn.noobbb.coupon.template.dao.CouponTemplateDao;
import cn.noobbb.coupon.template.dao.entity.CouponTemplate;
import cn.noobbb.coupon.template.service.intf.CouponTemplateServiceTCC;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
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
public class CouponTemplateServiceImpl implements CouponTemplateServiceTCC {

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
        BeanUtils.copyProperties(template, newOne);
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


    /**
     * 让优惠券模板无效
     */
    @Override
    @Transactional
    public void deleteTemplate(Long id) {
        int rows = templateDao.makeCouponUnavailable(id);
        if (rows == 0) {
            throw new IllegalArgumentException("找不到模板 ID: " + id);
        }
    }


    /**
     * 让优惠券模板无效 TCC
     * BusinessActionContextParameter ：标注需要传递的参数，在事务上下文（BusinessActionContext）中进行传递
     * 注意：@BusinessActionContextParameter 需要加在实现类上而不是接口
     */
    @Override
    @Transactional
    public void deleteTemplateTCC(BusinessActionContext context,@BusinessActionContextParameter(paramName = "id") Long id) {

        // 在 Try 阶段逻辑中，一般会先去预定操作资源

        // 这里筛选未被锁定且状态为 available 的券模板，并且将其 locked 状态置为 true
        // 在真实业务中，可能还需要记录操作者以及更多的信息，方便后续准确地回滚数据，例如转账时的交易号

        CouponTemplate filter = CouponTemplate.builder()
                .available(true)
                .locked(false)
                .id(id)
                .build();

        CouponTemplate template = templateDao.findAll(Example.of(filter))
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Template Not Found"));

        template.setLocked(true);
        templateDao.save(template);

//        if (true) {
//            throw new RuntimeException("Delete Template Failed");
//        }

        log.info("TCC Try");
    }

    @Override
    @Transactional
    public void deleteTemplateCommit(BusinessActionContext context) {

        // Confirm 阶段执行的是主体业务逻辑，即删除优惠券

        Long id = Long.parseLong(context.getActionContext("id").toString());
        CouponTemplate template = templateDao.findById(id).get();

//        if (true) {
//            throw new RuntimeException("Delete Template Failed");
//        }

        // 解锁操作
        template.setLocked(false);
        // 主体业务操作：删除优惠券
        template.setAvailable(false);
        templateDao.save(template);

        log.info("TCC committed");
    }

    @Override
    @Transactional
    public void deleteTemplateCancel(BusinessActionContext context) {

        // Cancel 阶段：如果在 Try 或者 Confirm 阶段发生了异常，就会触发 TCC 全局事务回滚，Seata Server 会将 Rollback 指令发送给每一个分支事务

        // 在线上业务中，Cancel 方法只能释放由当前 TCC 事务在 Try 阶段锁定的资源

        // 再次查询一次，避免空回滚
        Long id = Long.parseLong(context.getActionContext("id").toString());
        Optional<CouponTemplate> templateOption = templateDao.findById(id);

        if (templateOption.isPresent()) {
            CouponTemplate template = templateOption.get();
            // 解锁
            template.setLocked(false);
            templateDao.save(template);
        }
        log.info("TCC cancel");
    }

}
