package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    /**
     *
     * @description:新增菜品口味
     * @author: Cvvvv
     * @date: 2025/7/26 10:30
     * @param: [flavors]
     * @return: void
     */
     void addFlavorForDish(List<DishFlavor> flavors);
}
