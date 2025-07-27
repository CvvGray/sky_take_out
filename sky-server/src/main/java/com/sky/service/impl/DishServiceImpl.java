package com.sky.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;




    /**
     *
     * @description:新增菜品
     * @author: Cvvvv
     * @date: 2025/7/26 9:35
     * @param: [dishDTO]
     * @return: void
     */
    @Override
    @Transactional
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.addDish(dish);

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.addFlavorForDish(flavors);
        }


    }

    /**
     *
     * @description:菜品分页查询
     * @author: Cvvvv
     * @param: 菜品分页查询
     * @return: com.sky.result.PageResult
    */
    @Override
    public PageResult pageQueryDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQueryDish(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> records = page.getResult();
        return new PageResult(page.getTotal(), records);
    }

    /**

    /**
     *
     * @description:修改菜品状态
     * @author: Cvvvv
     * @param: [status, dishId]
     * @return: void
     */
    @Override
    public void updateDishStatus(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();

        dishMapper.updateDishInformation(dish);

    }

    /**
     *
     * @description:删除菜品和对应的口味数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: void
     */
    @Transactional
    @Override
    public void deleteDishAndFlavor(List<Long> ids) {
        //1.根据id查询菜品状态，起售的菜品不能删除
        List<Dish> dishes = dishMapper.queryDishByids(ids);
        String notAllowDeleteDishStr = "";
        for (Dish dish : dishes) {
            if (dish.getStatus().equals(StatusConstant.ENABLE) ) {
                notAllowDeleteDishStr += dish.getName()+",";
            }
        }
        if(notAllowDeleteDishStr.length()>0){
            throw new DeletionNotAllowedException(notAllowDeleteDishStr + MessageConstant.DISH_ON_SALE);
        }


        //2。根据id查询此菜品是否有被加入套餐里面，如果有，则不能删除
        List<SetmealDish> setmealDishes = setmealDishMapper.querySetmealByDishIds(ids);
        String notAllowDeleteStr = "";
        for (SetmealDish setmealDish : setmealDishes) {
            if (setmealDish.getSetmealId() != null) {
                notAllowDeleteStr += setmealDish.getName()+",";
            }
        }
        if(notAllowDeleteStr.length()>0){
            throw new DeletionNotAllowedException(notAllowDeleteStr + MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //3.先根据id删除菜品表中的数据
        dishMapper.deleteDishByIds(ids);


        //4.在根据菜品id删除口味表中的数据
        dishFlavorMapper.deleteFlavorByDishIds(ids);

    }

    /**
     *
     * @description:根据菜品id查询菜品及其口味相关数据,
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.vo.DishVO
     */
    @Override
    public DishVO queryDishAndFlavorByDishId(Long DishId) {
        DishVO dishVO = new DishVO();
        Dish dish  = dishMapper.queryDishById(DishId);
        BeanUtils.copyProperties(dish,dishVO);

        Category category = categoryMapper.queryCategoryById(dish.getCategoryId());
        dishVO.setCategoryName(category.getName());

        List<DishFlavor> dishFlavors =  dishFlavorMapper.queryFlavorByDishId(DishId);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }


    /**
     *
     * @description:修改菜品信息
     * @author: Cvvvv
     * @param: [dishDTO]
     * @return: void
     */
    @Override
    @Transactional
    public void updateDishInformation(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.updateDishInformation(dish);

        //删除原有的口味数据
        dishFlavorMapper.deleteFlavorByDishId(dishDTO.getId());

        //重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            //向口味表插入n条数据
            dishFlavorMapper.addFlavorForDish(flavors);
        }
    }
}
