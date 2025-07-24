package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "菜品分类模块")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProjectInfoAutoConfiguration projectInfoAutoConfiguration;

    /**
     *
     * @description:新增菜品分类
     * @author: Cvvvv
     * @date: 2025/7/24 17:17
     * @param: [categoryDTO]
     * @return: com.sky.result.Result
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增菜品，{}",categoryDTO);
        categoryService.addCategory(categoryDTO);

        return Result.success();
    }



    /**
     *
     * @description:分类分页查询
     * @author: Cvvvv
     * @date: 2025/7/24 20:38
     * @param: [categoryPageQueryDTO]
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {

        log.info("分类分页查询，{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQueryCategory(categoryPageQueryDTO);

        return Result.success(pageResult);
    }



    /**
     *
     * @description:修改分类状态
     * @author: Cvvvv
     * @date: 2025/7/24 20:42
     * @param: [status, id]
     * @return: com.sky.result.Result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改分类状态")
    public Result updateCategoryStatus(@PathVariable("status") Integer status, Long id) {
        log.info("修改分类状态，{}，{}",status,id);
        categoryService.updateCategoryStatus(status,id);

        return Result.success();

    }


}
