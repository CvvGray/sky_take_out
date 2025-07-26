package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DishMapper {


    /**
     * @description:新增菜品
     * @author: Cvvvv
     * @date: 2025/7/26 9:37
     * @param: [dish]
     * @return: void
     */
    @AutoFill(value = OperationType.INSERT)
    void addDish(Dish dish);






}
