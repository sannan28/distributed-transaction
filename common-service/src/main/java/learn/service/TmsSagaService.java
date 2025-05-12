package learn.service;


import learn.service.dto.ReceiveFulfillDto;

/**
 * tms saga service
 */
public interface TmsSagaService {


    /**
     * 发货
     */
    Boolean sendOut(ReceiveFulfillDto request);

    /**
     * 发货补偿
     */
    Boolean sendOutCompensate(ReceiveFulfillDto request);
}
