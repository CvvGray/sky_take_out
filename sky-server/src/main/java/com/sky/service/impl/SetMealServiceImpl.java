package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.xmlbeans.impl.schema.XQuerySchemaTypeSystem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    /**
     *
     * @description:套餐分页查询
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: com.sky.result.PageResult
     */
    @Override
    public PageResult pageSetMeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page = setMealMapper.pageQuerySetMeal(setmealPageQueryDTO);
        long total = page.getTotal();
        List<Setmeal> setmeals = page.getResult();
        return new PageResult<>(total,setmeals);
    }


    /**
     *
     * @description:新增套餐
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: void
     */
    @Override
    @Transactional
    public void addSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //向套餐表中添加数据
        setMealMapper.insertSetMeal(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        //向套餐和菜品关系表中添加数据
        setmealDishMapper.insertRelationship(setmealDishes);
    }


    /**
     *
     * @description:根据套餐id查询套餐信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.vo.SetmealVO
     */
    @Override
    public SetmealVO getSetMealById(Long setmealId) {
        //查询套餐表
        SetmealVO setmealVO = setMealMapper.getSetMealById(setmealId);
        //查询套餐与菜品关联表
        List<SetmealDish> setmealDishes = setmealDishMapper.queryDishesBySetMealIdAdmin(setmealId);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }


    /**
     *
     * @description:根据套餐id修改套餐
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: void
     */
    @Transactional
    @Override
    public void updateSetMealById(SetmealDTO setmealDTO) {
        //TODO
        //先删除套餐数据
        List<Long> setMealIds = new ArrayList<>();
        setMealIds.add(setmealDTO.getId());
        setMealMapper.deleteSetMealByIds(setMealIds);
        //再删除套餐与菜品相关数据
        setmealDishMapper.deleteDishsBySetMealIds(setMealIds);

        //新增套餐数据
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setMealMapper.insertSetMeal(setmeal);
        //新增套餐与菜品相关数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        setmealDishMapper.insertRelationship(setmealDishes);

    }



    /**
     *
     * @description:批量删除套餐
     * @author: Cvvvv
     * @param: [ids]
     * @return: void
     */
    @Override
    public void deleteSetMeal(List<Long> ids) {
        //1.根据id查询菜品状态，起售的套餐不能删除
        List<Setmeal> setmeals = setMealMapper.querySetMealByIds(ids);
        String notAllowDeleteDishStr = "";
        for (Setmeal setmeal : setmeals) {
            if (setmeal.getStatus().equals(StatusConstant.ENABLE) ) {
                notAllowDeleteDishStr += setmeal.getName()+",";
            }
        }
        if(notAllowDeleteDishStr.length()>0){
            throw new DeletionNotAllowedException(notAllowDeleteDishStr + MessageConstant.SETMEAL_ON_SALE);
        }

        //2.先根据id删除套餐表中的数据
        setMealMapper.deleteSetMealByIds(ids);

        //3.在根据套餐id删除套餐和菜品表中的数据
        setmealDishMapper.deleteDishsBySetMealIds(ids);
    }

    /**
     *
     * @description:修改套餐状态
     * @author: Cvvvv
     * @param: [status, id]
     * @return: void
     */
    @Override
    public void updateSetMealStatus(Integer status, Long id) {
        setMealMapper.updateSetMealStatus(status,id);
    }
}
