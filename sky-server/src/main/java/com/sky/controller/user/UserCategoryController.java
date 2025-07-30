package com.sky.controller.user;

import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/category")
@Slf4j
@Api(tags = "用户菜品分类相关")
public class UserCategoryController {

    @Autowired
    public CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("展示分类信息")
    public Result<List> showCategoryList(Category category) {
        log.info("用户查询分类的信息：{}", category.getType());
        List<Category> categoryList = categoryService.queryCategoryByType(category.getType());
        return Result.success(categoryList);
    }


}
