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


    /**
     *
     * @description:修改分类状态
     * @author: Cvvvv
     * @date: 2025/7/24 20:43
     * @param: [status, id]
     * @return: void
     */
    void updateCategoryStatus(Integer status, Long id);

    /**
     *
     * @description:根据id删除分类
     * @author: Cvvvv
     * @date: 2025/7/24 21:24
     * @param: [id]
     * @return: void
     */
   void deleteCategoryById(Long id);

   /**
    *
    * @description:根据id修改分类信息
    * @author: Cvvvv
    * @date: 2025/7/24 21:33
    * @param: [categoryDTO]
    * @return: void
    */
   void updateCategoryInformation(CategoryDTO categoryDTO);
}
