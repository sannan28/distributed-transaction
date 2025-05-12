package learn.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import learn.service.InventoryService;
import learn.service.OrderService;
import learn.service.ShopCartService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@DubboService
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DubboReference
    private InventoryService inventoryService;

    @DubboReference
    private ShopCartService shopCartService;

    @Transactional
    @GlobalTransactional
    public boolean createOrder(int cartId) {
        // 简单的模拟下单流程，包括服务间的调用流程。

        // 调用购物车服务-获取即将操作的goods_id
        int goodsId = shopCartService.getGoodsId(cartId);

        // 调用商品服务-减库存
//        Boolean goodsResult = inventoryService.deStock(goodsId);
        // 调用商品服务-TCC减库存
        Boolean goodsResult = inventoryService.deStockTcc(String.valueOf(goodsId), 10);
        // 调用购物车服务-删除当前购物车数据
        Boolean cartResult = shopCartService.deleteItem(cartId);

        // 执行下单逻辑
        if (goodsResult && cartResult) {
            // 向订单表中新增一条记录
            int orderResult = jdbcTemplate.update("insert into tb_order(`cart_id`) value (\"" + cartId + "\")");
            if (orderResult > 0) {
//                throw  new RuntimeException();
                return true;
            }
            return false;
        }
        return false;
    }
}
