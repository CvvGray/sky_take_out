package com.sky.mapper;


import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     *
     * @description:查询购物车中的数据
     * @author: Cvvvv
     * @param: [shoppingCart]
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     */
    List<ShoppingCart> queryShoppingCartDishOrSetmeal(ShoppingCart shoppingCart);


    /**
     *
     * @description:修改购物车中对应商品的数量
     * @author: Cvvvv
     * @param: [shoppingCart]
     * @return: void
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     *
     * @description:添加一个商品到购物车中
     * @author: Cvvvv
     * @param: [shoppingCart]
     * @return: void
     */
    @Insert("insert into shopping_cart(id,name,image,user_id,dish_id,setmeal_id,dish_flavor,number,amount,create_time)" +
            "values " +
            "(#{id},#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void addADishOrSetmeal(ShoppingCart shoppingCart);

    /**
     *
     * @description:根据用户id查询购物车中的数据
     * @author: Cvvvv
     * @param: [currentUserId]
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     */
    @Select("select id,name,image,user_id,dish_id,setmeal_id,dish_flavor,number,amount,create_time from shopping_cart " +
            "where user_id=#{userId}")
    List<ShoppingCart> queryShoppingCartByUserId(Long currentUserId);


    /**
     *
     * @description:根据用户Id删除购物车中的数据
     * @author: Cvvvv
     * @param: [shoppingCart]
     * @return: void
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteShoppingCart(Long userId);
}
