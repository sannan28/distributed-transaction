package learn.service;


import learn.service.dto.ReceiveFulfillDto;

/**
 * fulfull saga service
 */
public interface FulfillSagaService {

    /**
     * 创建履约单
     */
    Boolean createFulfillOrder(ReceiveFulfillDto receiveFulfillDto);


    /**
     * 补偿创建履约单
     */
    Boolean createFulfillOrderCompensate(ReceiveFulfillDto receiveFulfillDto);
}
