package com.sky.service;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

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

    /**
     *
     * @description:套餐分页查询
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: com.sky.result.PageResult
     */
    PageResult pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     *
     * @description:新增套餐
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: void
     */
    void addSetMeal(SetmealDTO setmealDTO);

    /**
     *
     * @description:根据套餐id查询套餐信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.vo.SetmealVO
     */
    SetmealVO getSetMealById(Long setmealId);

    /**
     *
     * @description:根据套餐id修改套餐
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: void
     */
    void updateSetMealById(SetmealDTO setmealDTO);

    /**
     *
     * @description:批量删除套餐
     * @author: Cvvvv
     * @param: [ids]
     * @return: void
     */
    void deleteSetMeal(List<Long> ids);

    /**
     *
     * @description:修改套餐状态
     * @author: Cvvvv
     * @param: [status, id]
     * @return: void
     */
    void updateSetMealStatus(Integer status, Long id);
}
