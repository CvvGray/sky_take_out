package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;

import java.util.List;

public interface SetMealService {

    /**
     *
     * @description:根绝setmealDTO中的[id],[name],[status],[categoryId]查询套餐数据，以上参数可有可无
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: java.util.List<com.sky.entity.Setmeal>
     */
    List<Setmeal> querySetMeal(SetmealDTO setmealDTO);

    /**
     *
     * @description:根据套餐id查询包含的菜品
     * @author: Cvvvv
     * @param: [setmealId]
     * @return: java.util.List<com.sky.entity.SetmealDish>
     */
    List<DishItemVO> showDishesBySetMealId(Long setmealId);
}
