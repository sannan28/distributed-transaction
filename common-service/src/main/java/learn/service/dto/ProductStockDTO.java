package learn.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductStockDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 商品sku编号
     */
    private String goodsId;

    /**
     * 销售库存
     */
    private Long saleStockNum;

    /**
     * 已销售库存
     */
    private Long saledStockNum;
}
