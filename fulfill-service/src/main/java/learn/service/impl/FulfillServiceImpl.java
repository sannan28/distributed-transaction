package learn.service.impl;

import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import learn.service.FulfillService;
import learn.service.dto.ReceiveFulfillDto;
import learn.util.SpringApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@DubboService
public class FulfillServiceImpl implements FulfillService {
    @Autowired
    private SpringApplicationContext springApplicationContext;


    public Boolean receiveOrderFulFill(ReceiveFulfillDto request) {
        StateMachineEngine stateMachineEngine = (StateMachineEngine) springApplicationContext
                .getBean("stateMachineEngine");
        Map<String, Object> startParams = new HashMap<String,Object>(3);
        startParams.put("receiveFulfillRequest", request);

        //配置的saga状态机 json的name
        // 位于/resources/statelang/order_fulfull.json
        String stateMachineName = "order_fulfill";
        log.info("开始触发saga流程，stateMachineName={}", stateMachineName);
        StateMachineInstance inst = stateMachineEngine.startWithBusinessKey(stateMachineName, null, null, startParams);
        if (ExecutionStatus.SU.equals(inst.getStatus())) {
            log.info("订单履约流程执行完毕. xid={}", inst.getId());
        } else {
            log.error("订单履约流程执行异常. xid={}", inst.getId());
            throw new RuntimeException("订单履约流程执行异常");
        }
        return true;
    }
}
