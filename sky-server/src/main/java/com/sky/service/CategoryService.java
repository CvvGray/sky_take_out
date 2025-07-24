package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {


 /**
  *
  * @description:新增分类
  * @author: Cvvvv
  * @author: Cvvvv 
  * @date: 2025/7/24 16:23
  * @param: [categoryDTO]
  * @return: void
  */
    void addCategory(CategoryDTO categoryDTO);

    
    /**
     *
     * @description:分类分页查询
     * @author: Cvvvv 
     * @date: 2025/7/24 17:23
     * @param: [categoryPageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    PageResult pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO);
}
