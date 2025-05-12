package learn.service;

import learn.service.dto.ReceiveFulfillDto;

public interface FulfillService {
    /**
     * 触发履约
     */
    Boolean receiveOrderFulFill(ReceiveFulfillDto request);
}
