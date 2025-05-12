package learn.service.impl;


import learn.service.InventoryService;
import learn.service.dto.DeductStockDTO;
import learn.service.dto.ProductStockDTO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@DubboService
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DeductProductStockExecutor deductProductStockExecutor;


    public void deStock(DeductStockDTO deductStockdto) {
        deductProductStockExecutor.doDeduct(deductStockdto);

    }

    public Boolean deStockTcc(String goodsId, Integer saleNum) {
        //1：查询当前商品库存
        ProductStockDTO productStockdto = jdbcTemplate.
                queryForObject("select * from inventory_product_stock where goodsId =?",
                        new BeanPropertyRowMapper<ProductStockDTO>(ProductStockDTO.class), goodsId);
        DeductStockDTO deductStockDTO = new DeductStockDTO();
        deductStockDTO.setGoodsId(goodsId);
        deductStockDTO.setSaledStock(productStockdto.getSaledStockNum().intValue());
        deductStockDTO.setSaleStock(productStockdto.getSaleStockNum().intValue());
        deductStockDTO.setSaleNum(saleNum);
        deStock(deductStockDTO);
        return true;
    }
}
