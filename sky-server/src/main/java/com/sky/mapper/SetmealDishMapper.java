package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     *
     * @description:根据菜品id查询套餐和菜品关联的数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: java.util.List<com.sky.entity.SetmealDish>
     */
    List<SetmealDish> querySetmealByDishIds(List<Long> dishIds);
}
