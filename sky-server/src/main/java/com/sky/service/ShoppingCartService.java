package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {


    /**
     *
     * @description:购物车添加菜品/套餐
     * @author: Cvvvv
     * @param: [shoppingCartDTO]
     * @return: void
     */
    void addToShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     *
     * @description:展示购物车中的数据
     * @author: Cvvvv
     * @param: []
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     */
    List<ShoppingCart> showShoppingCart();

    /**
     *
     * @description:删除购物车中一个商品
     * @author: Cvvvv
     * @param: [shoppingCartDTO]
     * @return: void
     */
    void subToShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     *
     * @description:清空购物车
     * @author: Cvvvv
     * @param: []
     * @return: void
     */
    void cleanShoppingCart();
}
