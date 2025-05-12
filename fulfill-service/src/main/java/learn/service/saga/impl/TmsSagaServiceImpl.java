package learn.service.saga.impl;

import com.alibaba.fastjson.JSONObject;
import com.imooc.fulfill.service.dto.ReceiveFulfillDto;
import com.imooc.fulfill.service.saga.TmsSagaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 */
@Service("tmsSagaService")
@Slf4j
public class TmsSagaServiceImpl implements TmsSagaService {

    @Override
    public Boolean sendOut(ReceiveFulfillDto request) {
        log.info("发货，request={}", JSONObject.toJSONString(request));
//        throw new RuntimeException("发货 异常了");
        return true;
    }

    @Override
    public Boolean sendOutCompensate(ReceiveFulfillDto request) {
        log.info("补偿发货，request={}", JSONObject.toJSONString(request));
        return true;
    }

}
