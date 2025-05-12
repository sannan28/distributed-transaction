package learn.service;


import learn.service.dto.ReceiveFulfillDto;

/**
 * wms的saga service
 */
public interface WmsSagaService {

    // 捡货
    Boolean pickGoods(ReceiveFulfillDto receiveFulfillDto);

    // 捡货补偿
    Boolean pickGoodsCompensate(ReceiveFulfillDto receiveFulfillDto);
}
