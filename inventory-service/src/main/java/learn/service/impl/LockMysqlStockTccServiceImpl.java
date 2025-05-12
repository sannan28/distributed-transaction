package learn.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.seata.rm.tcc.api.BusinessActionContext;
import learn.service.dto.DeductStockDTO;
import learn.service.tcc.LockMysqlStockTccService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LockMysqlStockTccServiceImpl implements LockMysqlStockTccService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean deductStock(BusinessActionContext actionContext, DeductStockDTO deductStock) {
        log.info("tcc mysql 第一阶段开始执行");

        //商品编码
        String goodsId = deductStock.getGoodsId();
        //销售数量
        Integer saleNum = deductStock.getSaleNum();
        //库存的销售数量
        Integer saleStock = deductStock.getSaleStock();

        String sql = "update inventory_product_stock \n" +
                "set sale_stock_num = sale_stock_num - ? \n" +
                "where goodsId = ? \n" +
                "and sale_stock_num >= ? \n" +
                "and sale_stock_num = ?";

        int update = jdbcTemplate.update(sql, saleNum, goodsId, saleNum, saleStock);

        return update > 0;
    }



    public void commit(BusinessActionContext actionContext) {
        log.info("tcc mysql 第二阶段开始执行");
        DeductStockDTO deductStock = ((JSONObject) actionContext.getActionContext("deductStock")).toJavaObject(DeductStockDTO.class);

        String goodsId = deductStock.getGoodsId();
        Integer saleNum = deductStock.getSaleNum();
        Integer saledStock = deductStock.getSaledStock();

        String sql = "update inventory_product_stock \n" +
                "set saled_stock_num = saled_stock_num + ? \n" +
                "where goodsId = ? \n" +
                "and saled_stock_num = ?";

        jdbcTemplate.update(sql, saleNum, goodsId, saledStock);
    }

    public void rollback(BusinessActionContext actionContext) {
        log.info("tcc mysql 第二阶段回滚开始执行");
        DeductStockDTO deductStock = ((JSONObject) actionContext.getActionContext("deductStock")).toJavaObject(DeductStockDTO.class);
        String goodsId = deductStock.getGoodsId();
        Integer saleNum = deductStock.getSaleNum();
        Integer saledStock = deductStock.getSaledStock();

        String sql = "update inventory_product_stock \n" +
                "set sale_stock_num = sale_stock_num + ? \n" +
                "where goodsId = ? \n" +
                "and saled_stock_num = ?\n";
        jdbcTemplate.update(sql, saleNum, goodsId, saledStock);

    }
}
