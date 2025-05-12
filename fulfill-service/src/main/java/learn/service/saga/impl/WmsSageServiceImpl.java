package learn.service.saga.impl;

import com.alibaba.fastjson.JSONObject;
import com.imooc.fulfill.service.dto.ReceiveFulfillDto;
import com.imooc.fulfill.service.saga.WmsSagaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("wmsSageService")
@Slf4j
public class WmsSageServiceImpl implements WmsSagaService {


    @Override
    public Boolean pickGoods(ReceiveFulfillDto request) {
        log.info("捡货，request={}",    JSONObject.toJSONString(request));
        return true;
    }

    @Override
    public Boolean pickGoodsCompensate(ReceiveFulfillDto request) {
        log.info("补偿捡货，request={}", JSONObject.toJSONString(request));
        return true;
    }

}
