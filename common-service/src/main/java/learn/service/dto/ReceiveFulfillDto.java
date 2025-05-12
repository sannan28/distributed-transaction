package learn.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiveFulfillDto implements Serializable {
    /**
     * 订单号
     */
    private String orderId;

    /**
     * 用户id
     */
    private String userId;


    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;
}
