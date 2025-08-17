package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;


    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询,{}", setmealPageQueryDTO.toString());
        PageResult pageResult = setMealService.pageSetMeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }




    @PostMapping
    @ApiOperation("新增套餐")
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐,{}", setmealDTO.toString());
        setMealService.addSetMeal(setmealDTO);
        return Result.success();
    }


    /**
     *
     * @description:根据id查询套餐信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.result.Result<com.sky.vo.SetmealVO>
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getSetMealById(@PathVariable Long id) {
        log.info("根据id查询套餐,{}",id);
        SetmealVO setmealVO = setMealService.getSetMealById(id);
        return Result.success(setmealVO);
    }



    /**
     *
     * @description:修改套餐
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: com.sky.result.Result
     */
    @PutMapping
    @ApiOperation("修改套餐")
    public Result updateSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐,{}", setmealDTO.toString());
        setMealService.updateSetMealById(setmealDTO);
        return Result.success();
    }


    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteSetMeal(@RequestParam List<Long> ids) {
        log.info("批量删除,{}", ids.toString());
        setMealService.deleteSetMeal(ids);
        return Result.success();
    }



    /**
     *
     * @description:修改套餐状态
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.result.Result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改套餐状态")
    public Result updateSetMealStatus(@PathVariable("status") Integer status, Long id) {

        setMealService.updateSetMealStatus(status,id);

        return Result.success();
    }





}
