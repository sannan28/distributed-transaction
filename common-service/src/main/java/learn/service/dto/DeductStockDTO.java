package learn.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeductStockDTO implements Serializable {
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 商品goodsId
     */
    private String goodsId;

    /**
     * 销售数量
     */
    private Integer saleNum;

    /**
     * 原始销售库存
     */
    private Integer saleStock;

    /**
     * 原始已销售库存
     */
    private Integer saledStock;

}
