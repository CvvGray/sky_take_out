package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    public CategoryMapper categoryMapper;


    /**
     *
     * @description:新增分类
     * @author: Cvvvv
     * @date: 2025/7/24 16:24
     * @param: [categoryDTO]
     * @return: void
     */
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(StatusConstant.DISABLE);

        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.addCategory(category);


    }

    /**
     *
     * @description:分类分页查询
     * @author: Cvvvv
     * @date: 2025/7/24 17:24
     * @param: [categoryPageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    @Override
    public PageResult pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page page = categoryMapper.pageQueryCategory(categoryPageQueryDTO);
        long total = page.getTotal();
        List results = page.getResult();

        return new PageResult(total,results);
    }

    /**
     *
     * @description:修改分类状态
     * @author: Cvvvv
     * @date: 2025/7/24 20:44
     * @param: [status, id]
     * @return: void
     */
    @Override
    public void updateCategoryStatus(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();

        categoryMapper.updateCategoryStatus(category);


    }
}
