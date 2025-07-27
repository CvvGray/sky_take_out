package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


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



    /**
     *
     * @description:菜品分页查询
     * @author: Cvvvv
     * @param: [dishPageQueryDTO]
     * @return: com.github.pagehelper.Page<com.sky.vo.DishVO>
     */
    Page<DishVO> pageQueryDish(DishPageQueryDTO dishPageQueryDTO);

    /**
     *
     * @description:修改菜品状态
     * @author: Cvvvv
     * @param: [dish]
     * @return: void
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateDishInformation(Dish dish);

    /**
     *
     * @description:根据多个id批量查询菜品
     * @author: Cvvvv
     * @param: [ids]
     * @return: java.util.List<com.sky.entity.Dish>
     */
    List<Dish> queryDishByids(List<Long> ids);


    /**
     *
     * @description:根据多个菜品id批量删除菜品
     * @author: Cvvvv
     * @param: [ids]
     * @return: void
     */
    void deleteDishByIds(List<Long> ids);



    /**
     *
     * @description:根据id查询单个菜品信息
     * @author: Cvvvv
     * @param: [dishId]
     * @return: com.sky.entity.Dish
     */
    @Select("select id,name,image,price,status,update_time,category_id,description " +
            "from dish d " +
            "where d.id = #{dishId}")
    Dish queryDishById(Long id);
}
