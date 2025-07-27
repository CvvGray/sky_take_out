package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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


    /**
     *
     * @description:根据菜品id，删除口味表中的数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: void
     */
    @Delete("delete from dish_flavor where dish_id=#{id}")
    void deleteFlavorByDishId(Long id);



    /**
     *
     * @description:根据菜品信息查询口味信息
     * @author: Cvvvv
     * @param: [dishId]
     * @return: java.util.List<com.sky.entity.DishFlavor>
     */
    @Select("select dish_id,id,name,value from dish_flavor WHERE dish_id = #{dishId}")
    List<DishFlavor> queryFlavorByDishId(Long dishId);
}
