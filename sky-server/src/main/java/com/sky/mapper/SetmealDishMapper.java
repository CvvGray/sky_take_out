package com.sky.mapper;

import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     *
     * @description:根据菜品id查询套餐和菜品关联的数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: java.util.List<com.sky.entity.SetmealDish>
     */
    List<SetmealDish> querySetmealByDishIds(List<Long> dishIds);



    /**
     *
     * @description:根据套餐id查询包含的菜品
     * @author: Cvvvv
     * @param: [setmealId]
     * @return: java.util.List<com.sky.entity.SetmealDish>
     */
    @Select("select s.name,s.copies,d.image,d.description " +
            "from setmeal_dish s left join dish d on s.dish_id = d.id " +
            "where s.setmeal_id = #{setmealId}")
    List<DishItemVO> queryDishesBySetMealId(Long setmealId);
}
