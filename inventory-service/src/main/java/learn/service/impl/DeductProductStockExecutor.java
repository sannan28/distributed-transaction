package learn.service.impl;

import io.seata.rm.tcc.api.BusinessActionContext;
import learn.service.dto.DeductStockDTO;
import learn.service.tcc.LockMysqlStockTccService;
import learn.service.tcc.LockRedisStockTccService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 扣减商品库存处理器
 */
@Slf4j
@Component
public class DeductProductStockExecutor {
    @Autowired
    private LockMysqlStockTccService lockMysqlStockTccService;

    @Autowired
    private LockRedisStockTccService lockRedisStockTccService;

    /**
     * 执行扣减商品库存逻辑
     */
    public void doDeduct(DeductStockDTO deductStock) {
        // 1、执行执行mysql库存扣减
        boolean result = lockMysqlStockTccService.deductStock(new BusinessActionContext(), deductStock);
        if (!result) {
            throw new RuntimeException("lockMysqlStockTccService deductStock异常");
        }

        // 2、执行redis库存扣减
        result = lockRedisStockTccService.deductStock(new BusinessActionContext(), deductStock);
        if (!result) {
            log.info("lockRedisStockTccService.deductStock ok");
        }
    }

}
