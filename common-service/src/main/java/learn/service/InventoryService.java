package learn.service;

import learn.service.dto.DeductStockDTO;
import org.springframework.web.bind.annotation.PathVariable;

public interface InventoryService {
    // 扣减库存
    void deStock(DeductStockDTO deductStockdto);

    Boolean deStockTcc(@PathVariable("goodsId") String goodsId, @PathVariable("saleNum") Integer saleNum);
}
