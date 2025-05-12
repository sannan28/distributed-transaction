package learn.script;

/**
 * lua脚本
 */
public interface RedisLuaScript {

    /**
     * 调整商品库存
     */
    String MODIFY_PRODUCT_STOCK =
            "local productStockKey = KEYS[1];"
                    + "local saleStockKey = KEYS[2];"
                    + "local originSaleStockQuantity = tonumber(ARGV[1]);"
                    + "local stockIncremental = tonumber(ARGV[2]);"
                    + "local saleStock = tonumber(redis.call('hget', productStockKey, saleStockKey));"
                    + "if saleStock ~= originSaleStockQuantity then"
                    + "   return -1;"
                    + "end;"
                    + "if (saleStock+stockIncremental) < 0 then "
                    + "   return -1;"
                    + "end;"
                    + "redis.call('hset', productStockKey, saleStockKey, saleStock + stockIncremental);"
                    + "return 1;";

    /**
     * 扣减商品库存
     */
    String DEDUCT_PRODUCT_STOCK =
            "local productStockKey = KEYS[1];"
                    + "local saleStockKey = KEYS[2];"
                    + "local saledStockKey = KEYS[3];"
                    + "local saleQuantity = tonumber(ARGV[1]);"
                    + "local saleStock   = tonumber(redis.call('hget', productStockKey, saleStockKey));"
                    + "local saledStock = tonumber(redis.call('hget', productStockKey, saledStockKey));"
                    + "if saleStock < saleQuantity then"
                    + "   return -1;"
                    + "end;"
                    + "redis.call('hset', productStockKey, saleStockKey,   saleStock - saleQuantity);"
                    + "redis.call('hset', productStockKey, saledStockKey, saledStock + saleQuantity);"
                    + "return 1;";

    /**
     * 扣减销售库存
     */
    String DEDUCT_SALE_STOCK =
            //获取当前商品redis key
            "local productStockKey = KEYS[1];"
            //获取当前可售库存的key
            + "local saleStockKey = KEYS[2];"
            //获取当前扣减销售数量
            + "local saleNum = tonumber(ARGV[1]);"
            //获取当前原始销售库存
            + "local saleStock = tonumber(ARGV[2]);"
            //获取redis中销售库存值
            + "local result   = tonumber(redis.call('hget', productStockKey, saleStockKey));"
            //如果销售库存小于当前下单的库存扣减数量
            + "if result < saleNum then"
            + "   return -1;"
            + "end;"
            //如果销售库存不等于原来的销售库存(说明已经被其他线程修改了,不进行扣减)
            + "if result ~= saleStock then"
            + "   return -1;"
            + "end;"
            //扣减当前销售库存数量
            + "redis.call('hset', productStockKey, saleStockKey,   result - saleNum);"
            + "return 1;";

    /**
     * 增加已销售库存
     */
    String INCREASE_SALED_STOCK =
            //获取当前商品redis key
            "local productStockKey = KEYS[1];"
                    //获取已售库存的key
                    + "local saledStockKey = KEYS[2];"
                    //获取当前扣减销售数量
                    + "local saleNum = tonumber(ARGV[1]);"
                    //获取原始的已售库存
                    + "local saledStock = tonumber(ARGV[2]);"
                    //获取redis中原始的已售库存的值
                    + "local result = tonumber(redis.call('hget', productStockKey, saledStockKey));"
                    //如果已售销售库存不等于原来的已售销售库存(说明已经被其他线程修改了,不进行扣减)
                    + "if result ~= saledStock then"
                    + "   return -1;"
                    + "end;"
                    //累加已售库存的数量
                    + "redis.call('hset', productStockKey, saledStockKey, saledStock + saleNum);"
                    + "return 1;";

    /**
     * 还原销售库存
     */
    String RESTORE_SALE_STOCK =
            //获取当前商品redis key
            "local productStockKey = KEYS[1];"
                    //获取销售售库存的key
                    + "local saleStockKey = KEYS[2];"
                    //获取当前扣减销售数量
                    + "local saleNum = tonumber(ARGV[1]);"
                    //获取当前原始销售库存
                    + "local saleStock = tonumber(ARGV[2]);"
                    //获取redis中销售库存值
                    + "local result   = tonumber(redis.call('hget', productStockKey, saleStockKey));"
                    //如果销售库存不等于原来的销售库存(说明已经被其他线程修改了,不进行扣减)
                    + "if saleStock ~= result then"
                    + "   return -1;"
                    + "end;"
                    //还原销售库存
                    + "redis.call('hset', productStockKey, saleStockKey,   saleStock + saleNum);"
                    + "return 1;";

}
