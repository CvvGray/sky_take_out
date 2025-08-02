package com.sky.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    /**
     *
     * @description:购物车添加菜品/套餐
     * @author: Cvvvv
     * @param: [shoppingCartDTO]
     * @return: void
     */
    @Override
    public void addToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //根据userID,dishId,flavor查询数据库中是否有数据
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.queryShoppingCartDishOrSetmeal(shoppingCart);

        //如果存在，则数量+1
        if (shoppingCartList.size() > 0) {
            shoppingCart = shoppingCartList.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(shoppingCart);
        }else{
            //如果不存在，插入数据，数量就是1
            Long dishId = shoppingCart.getDishId();
            //添加的是菜品
            if (dishId != null) {
                Dish dish = dishMapper.queryDishById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());

            }else{
                //添加的是套餐
                SetmealDTO setmealDTO = new SetmealDTO();
                setmealDTO.setId(shoppingCart.getSetmealId());
                List<Setmeal> setmeals = setMealMapper.querySetMeal(setmealDTO);
                Setmeal setmeal = setmeals.get(0);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.addADishOrSetmeal(shoppingCart);
        }

    }


    /**
     *
     * @description:展示购物车中的数据
     * @author: Cvvvv
     * @param: []
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        Long currentUserId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(currentUserId);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.queryShoppingCartDishOrSetmeal(shoppingCart);
        return shoppingCarts;
    }

    /**
     *
     * @description:删除购物车中一个商品
     * @author: Cvvvv
     * @param: [shoppingCartDTO]
     * @return: void
     */

    @Override
    public void subToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long currentUserId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentUserId);

        List<ShoppingCart> shoppingCarts = shoppingCartMapper.queryShoppingCartDishOrSetmeal(shoppingCart);
        shoppingCart = shoppingCarts.get(0);
        if (shoppingCart == null){
            throw new ShoppingCartBusinessException("购物车数据异常");
        }

        shoppingCart.setNumber( shoppingCart.getNumber() - 1);
        if(shoppingCart.getNumber() - 1  <= 0 ){
            shoppingCartMapper.deleteShoppingCart(shoppingCart);
        }else {
            shoppingCartMapper.updateNumberById(shoppingCart);
        }

    }


    /**
     *
     * @description:清空购物车
     * @author: Cvvvv
     * @param: []
     * @return: void
     */
    @Override
    public void cleanShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCartMapper.deleteShoppingCart(shoppingCart);
    }
}
