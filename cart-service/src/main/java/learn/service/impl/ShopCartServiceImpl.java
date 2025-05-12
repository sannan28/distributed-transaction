package learn.service.impl;

import learn.service.ShopCartService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@DubboService
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 根据主键查询购物表
    public int getGoodsId(int cartId) {

        Map<String, Object> cartItemObject = jdbcTemplate.queryForMap("select * from tb_cart_item where cart_id =" + cartId + " limit 1");
        if (cartItemObject.containsKey("goods_id")) {
            return (Integer) cartItemObject.get("goods_id");
        }
        return 0;
    }

    // 根据主键删除购物车
    public Boolean deleteItem(int cartId) {
        int result = jdbcTemplate.update("delete from tb_cart_item where cart_id=" + cartId);
        if (result > 0) {
            return true;
        }
        return false;
    }
}
