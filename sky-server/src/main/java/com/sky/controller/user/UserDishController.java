package com.sky.controller.user;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.lettuce.core.output.ListOfGenericMapsOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "用户菜品相关")
public class UserDishController {

    @Autowired
    private DishService dishService;

    /**
     *
     * @description:  根据分类id查询查询菜品
     * @author: Cvvvv
     * @param: [dishDTO]
     * @return: com.sky.result.Result<java.util.List>
     */

    @GetMapping("/list")
    @ApiOperation("菜品展示")
    public Result<List> showDish(DishDTO dishDTO){
        log.info("根据分类id查询查询菜品：{}", dishDTO.getCategoryId());
        List<DishVO> dishList = dishService.queryDishByCategoryId(dishDTO.getCategoryId());
        return Result.success(dishList);
    }


}
