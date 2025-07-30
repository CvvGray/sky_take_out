package com.sky.service.impl;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import org.apache.xmlbeans.impl.schema.XQuerySchemaTypeSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;


    /**
     *
     * @description:根绝setmealDTO中的[id],[name],[status],[categoryId]查询套餐数据，以上参数可有可无
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: java.util.List<com.sky.entity.Setmeal>
     */
    @Override
    public List<Setmeal> querySetMeal(SetmealDTO setmealDTO) {
        List<Setmeal> setmeals =  setMealMapper.querySetMeal(setmealDTO);
        return setmeals;
    }


    @Override
    public List<DishItemVO> showDishesBySetMealId(Long setmealId) {
        List<DishItemVO> dishes = setmealDishMapper.queryDishesBySetMealId(setmealId);
        return dishes;
    }
}
