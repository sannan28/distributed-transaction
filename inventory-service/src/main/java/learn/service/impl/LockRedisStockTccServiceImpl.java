package learn.service.impl;


import com.alibaba.fastjson.JSONObject;
import io.seata.rm.tcc.api.BusinessActionContext;
import learn.script.RedisCache;
import learn.script.RedisLuaScript;
import learn.script.StockRedisUtils;
import learn.service.dto.DeductStockDTO;
import learn.service.tcc.LockRedisStockTccService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class LockRedisStockTccServiceImpl implements LockRedisStockTccService {

    @Autowired
    private RedisCache redisTemplate;

    public boolean deductStock(BusinessActionContext actionContext, DeductStockDTO deductStock) {
        log.info("tcc redis 第一阶段开始执行");
        //获取可售库存key
        String saleStockKey = StockRedisUtils.SALE_STOCK;
        //构造redis的key
        String productStockKey = StockRedisUtils.buildProductStockKey(deductStock.getGoodsId());
        //原始销售库存的扣减  -  销售数量
        Long execute = redisTemplate.execute(new DefaultRedisScript<Long>(RedisLuaScript.DEDUCT_SALE_STOCK,
                Long.class
        ), Arrays.asList(productStockKey, saleStockKey), String.valueOf(deductStock.getSaleNum()), String.valueOf(deductStock.getSaleStock()));
//        throw new RuntimeException("tcc redis 第一阶段异常执行");
        return execute > 0;
    }

    public void commit(BusinessActionContext actionContext) {
        log.info("tcc redis 第二阶段开始执行");
        //从下文中拿到库存信息
        DeductStockDTO deductStock = ((JSONObject) actionContext.getActionContext("deductStock")).toJavaObject(DeductStockDTO.class);
        //获取当前sku编码
        String goodsId = deductStock.getGoodsId();
        //销售数量
        Integer saleNum = deductStock.getSaleNum();
        //原始销售库存
        Integer saleStock = deductStock.getSaleStock();
        //原始已售库存
        Integer saledStock = deductStock.getSaledStock();
        //构造lua脚本
        String luaScript = RedisLuaScript.INCREASE_SALED_STOCK;
        //获取已售库存的key
        String saledStockKey = StockRedisUtils.SALED_STOCK;
        //构造rediskey
        String productStockKey = StockRedisUtils.buildProductStockKey(goodsId);
        Long execute = redisTemplate.execute(new DefaultRedisScript<Long>(luaScript, Long.class),
                Arrays.asList(productStockKey, saledStockKey),
                String.valueOf(saleNum), String.valueOf(saledStock));
    }

    public void rollback(BusinessActionContext actionContext) {
        log.info("tcc redis 第二阶段回滚开始执行");
        //从下文中拿到库存信息
        DeductStockDTO deductStock = ((JSONObject) actionContext.getActionContext("deductStock")).toJavaObject(DeductStockDTO.class);
        //获取当前商品编码
        String goodsId = deductStock.getGoodsId();
        //销售数量
        Integer saleNum = deductStock.getSaleNum();
        //原始销售库存
        Integer saleStock = deductStock.getSaleStock();
        //构造lua脚本
        String script = RedisLuaScript.RESTORE_SALE_STOCK;
        //获取已售库存的key
        String saleStockKey = StockRedisUtils.SALE_STOCK;
        //构造rediskey
        String productStockKey = StockRedisUtils.buildProductStockKey(goodsId);
        redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                Arrays.asList(productStockKey, saleStockKey), String.valueOf(saleNum), String.valueOf(saleStock - saleNum));
    }
}
