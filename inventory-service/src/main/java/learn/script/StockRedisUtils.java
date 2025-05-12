package learn.script;

import java.util.HashMap;
import java.util.Map;

public class StockRedisUtils {

    public static String PREFIX_PRODUCT_STOCK = "PRODUCT_STOCK";


    /**
     * 可销售库存key
     */
    public static String SALE_STOCK = "saleStock";
    /**
     * 已销售库存key
     */
    public static String SALED_STOCK = "saledStock";

    /**
     * 构造缓存商品库存key
     */
    public static String buildProductStockKey(String goodsId) {
        return PREFIX_PRODUCT_STOCK + ":" + goodsId;
    }

    /**
     * 构造缓存商品库存value
     */
    public static Map<String, String> buildProductStockValue(Long saleStock, Long saledStock) {
        Map<String, String> value = new HashMap<String, String>();
        value.put(SALE_STOCK, String.valueOf(saleStock));
        value.put(SALED_STOCK, String.valueOf(saledStock));
        return value;
    }
}
