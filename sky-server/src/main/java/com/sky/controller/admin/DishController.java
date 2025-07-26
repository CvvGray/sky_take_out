package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关")
public class DishController {

    @Autowired
    private DishService dishService;



    /**
     *
     * @description:新增菜品
     * @author: Cvvvv
     * @date: 2025/7/26 9:34
     * @param: [dishDTO]
     * @return: com.sky.result.Result
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}",dishDTO.toString());
        dishService.addDish(dishDTO);

        return Result.success();
    }


    /**
     *
     * @description:菜品分页查询
     * @author: Cvvvv
     * @date: 2025/7/26 12:09
     * @param: [dishPageQueryDTO]
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQueryDish(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询：{}",dishPageQueryDTO.toString());
        PageResult pageResult = dishService.pageQueryDish(dishPageQueryDTO);
        return Result.success(pageResult);

    }


    /**
     *
     * @description:修改菜品状态
     * @author: Cvvvv
     * @param:
     * @return:
     */

    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result updateDishStatus(@PathVariable("status") Integer status,Long id) {
        log.info("修改菜品状态，{},{}",status,id);
        dishService.updateDishStatus(status,id);
        return Result.success();
    }


    /**
     *
     * @description:删除菜品和对应的口味数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: com.sky.result.Result
     */
    @DeleteMapping
    @ApiOperation("删除菜品和对应的口味数据")
    public Result deleteDishAndFlavor(@RequestParam List<Long> ids) {
        log.info("需要删除的菜品有：{}",ids);
        dishService.deleteDishAndFlavor(ids);
        return Result.success();
    }

}
