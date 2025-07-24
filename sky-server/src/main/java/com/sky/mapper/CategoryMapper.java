package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper {

    /**
     *
     * @description:新增分类
     * @author: Cvvvv 
     * @date: 2025/7/24 17:28
     * @param: [category]
     * @return: void
     */
    @Insert("insert into category(type,name,sort,status,create_time, update_time, create_user, update_user)" +
            "values " +
            "(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addCategory(Category category);


    /**
     *
     * @description:分类分页查询
     * @author: Cvvvv
     * @date: 2025/7/24 17:29
     * @param: [categoryPageQueryDTO]
     * @return: com.github.pagehelper.Page
     */
    Page<Category> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO);


    /**
     *
     * @description:修改分类状态
     * @author: Cvvvv
     * @date: 2025/7/24 21:13
     * @param: [category]
     * @return: void
     */
    void updateCategoryStatus(Category category);
}
