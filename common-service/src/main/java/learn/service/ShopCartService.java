package learn.service;


import org.springframework.web.bind.annotation.PathVariable;

public interface ShopCartService {

    int getGoodsId(int cartId);

    Boolean deleteItem(int cartId);

}

