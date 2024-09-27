package cn.noobbb.coupon.template.service.intf;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

// 为了方便演示，新定义了一个接口类
// 如果 TCC 参与者是本地 bean（非远程RPC服务），则需要添加 @LocalTCC 注解
@LocalTCC
public interface CouponTemplateServiceTCC extends CouponTemplateService {

    // @TwoPhaseBusinessAction：使用 TCC 模式管理事务提交
    @TwoPhaseBusinessAction(
            name = "deleteTemplateTCC",
            useTCCFence = true,
            commitMethod = "deleteTemplateCommit",
            rollbackMethod = "deleteTemplateCancel"
    )
    void deleteTemplateTCC(BusinessActionContext context, Long id);

    void deleteTemplateCommit(BusinessActionContext context);

    void deleteTemplateCancel(BusinessActionContext context);
}
