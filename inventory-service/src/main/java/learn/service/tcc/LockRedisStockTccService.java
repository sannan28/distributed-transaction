package learn.service.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import learn.service.dto.DeductStockDTO;

@LocalTCC
public interface LockRedisStockTccService {
    /**
     * 一阶段方法：扣减销售库存
     *
     * @param actionContext
     * @return
     */
    @TwoPhaseBusinessAction(name = "lockRedisStockTccService", commitMethod = "commit", rollbackMethod = "rollback",useTCCFence = true)
    boolean deductStock(BusinessActionContext actionContext,
                        @BusinessActionContextParameter(paramName = "deductStock") DeductStockDTO deductStock);

    /**
     * 二阶段方法：增加已销售库存
     *
     * @param actionContext
     * @return
     */
    void commit(BusinessActionContext actionContext);

    /**
     * 回滚：增加销售库存
     *
     * @param actionContext
     * @return
     */
    void rollback(BusinessActionContext actionContext);

}
