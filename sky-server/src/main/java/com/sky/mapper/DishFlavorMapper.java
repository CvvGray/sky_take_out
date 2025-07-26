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

     /**
      *
      * @description:根据多个菜品id，批量删除口味表中的数据
      * @author: Cvvvv
      * @param: [ids]
      * @return: void
      */
    void deleteFlavorByDishIds(List<Long> dishIds);
}
