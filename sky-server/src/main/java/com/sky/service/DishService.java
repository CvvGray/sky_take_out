package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     *
     * @description:新增菜品
     * @author: Cvvvv
     * @date: 2025/7/26 9:35
     * @param: [dishDTO]
     * @return: void
     */
    void addDish(DishDTO dishDTO);

    /**
     *
     * @description:菜品分页查询
     * @author: Cvvvv
     * @param: [dishPageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    PageResult pageQueryDish(DishPageQueryDTO dishPageQueryDTO);

    /**
     *
     * @description:修改菜品状态
     * @author: Cvvvv
     * @param: [status, dishId]
     * @return: void
     */
    void updateDishStatus(Integer status, Long id);

    /**
     *
     * @description:删除菜品和对应的口味数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: void
     */
    void deleteDishAndFlavor(List<Long> ids);



    /**
     *
     * @description:根据菜品id查询菜品及其口味相关数据
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.vo.DishVO
     */
    DishVO queryDishAndFlavorByDishId(Long dishId);

    /**
     *
     * @description:修改菜品信息
     * @author: Cvvvv
     * @param: [dishDTO]
     * @return: void
     */
    void updateDishInformation(DishDTO dishDTO);

    /**
     *
     * @description: 根据分类id查询查询菜品
     * @author: Cvvvv
     * @param: [categoryId]
     * @return: java.util.List<com.sky.vo.DishVO>
     */
    List<DishVO> queryDishByCategoryId(Long categoryId);
}
