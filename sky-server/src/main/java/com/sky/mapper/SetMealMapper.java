package com.sky.mapper;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealMapper {


    /**
     *
     * @description:根绝setmealDTO中的[id],[name],[status],[categoryId]查询套餐数据，以上参数可有可无
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: java.util.List<com.sky.entity.Setmeal>
     */
    List<Setmeal> querySetMeal(SetmealDTO setmealDTO);
}
