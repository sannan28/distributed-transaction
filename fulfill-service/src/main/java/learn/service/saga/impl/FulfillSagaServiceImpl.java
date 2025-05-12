package learn.service.saga.impl;


import com.alibaba.fastjson.JSONObject;
import learn.service.FulfillService;
import learn.service.dto.ReceiveFulfillDto;
import learn.service.saga.FulfillSagaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service("fulfillSagaService")
public class FulfillSagaServiceImpl implements FulfillSagaService {

    @Autowired
    private FulfillService fulfillService;

    public Boolean createFulfillOrder(ReceiveFulfillDto receiveFulfillDto) {
        log.info("创建履约单，request={}", JSONObject.toJSONString(receiveFulfillDto));
        return true;
    }

    public Boolean createFulfillOrderCompensate(ReceiveFulfillDto receiveFulfillDto) {
        log.info("补偿创建履约单，request={}", JSONObject.toJSONString(receiveFulfillDto));
        return true;
    }


}
