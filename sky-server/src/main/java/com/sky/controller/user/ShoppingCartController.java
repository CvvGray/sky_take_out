package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "购物车相关接口")
public class ShoppingCartController {


    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     *
     * @description:购物车添加菜品/套餐
     * @author: Cvvvv
     * @param: [shoppingCartDTO]
     * @return: com.sky.result.Result
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车数据")
    public Result addToShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("往购物车中添加数据：{}", shoppingCartDTO.toString());
        shoppingCartService.addToShoppingCart(shoppingCartDTO);
        return Result.success();
    }




    /**
     *
     * @description:展示购物车中的数据
     * @author: Cvvvv
     * @param: []
     * @return: com.sky.result.Result<java.util.List>
     */
    @GetMapping("/list")
    @ApiOperation("展示当前用户购物车数据")
    public Result<List> showShoppingCart() {
        log.info("展示当前用户购物车数据");
        List<ShoppingCart> shoppingCarts = shoppingCartService.showShoppingCart();
        return Result.success(shoppingCarts);
    }


    /**
     *
     * @description:删除购物车中一个商品
     * @author: Cvvvv
     * @param: [shoppingCartDTO]
     * @return: com.sky.result.Result
     */
    @PostMapping("/sub")
    @ApiOperation("删除购物车中一个商品")
    public Result subToShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("删除购物车中一个商品：{}", shoppingCartDTO.toString());
        shoppingCartService.subToShoppingCart(shoppingCartDTO);
        return Result.success();
    }


    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result cleanShoppingCart() {
        log.info("清空购物车：");
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }



}
