package com.sky.controller.user;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "用户套餐相关")
public class UserSetMealController {

    @Autowired
    private SetMealService setMealService;

    /**
     *
     * @description:根据分类id查询套餐相关数据
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: com.sky.result.Result<java.util.List>
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐相关数据")
    public Result<List> showUserSetMealByCategoryId(SetmealDTO setmealDTO) {
        log.info("根据分类id查询套餐：{}", setmealDTO.getCategoryId());
        List<Setmeal> setmeals = setMealService.querySetMeal(setmealDTO);
        return Result.success(setmeals);


    }


    /**
     *
     * @description:根据套餐id查询包含的菜品
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: com.sky.result.Result<com.sky.entity.SetmealDish>
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List> showDishesBySetMealId(@PathVariable("id") Long setmealId) {
        log.info("根据套餐id查询包含的菜品,{}", setmealId);
        List<DishItemVO> dishes = setMealService.showDishesBySetMealId(setmealId);
        return Result.success(dishes);
    }

















}
