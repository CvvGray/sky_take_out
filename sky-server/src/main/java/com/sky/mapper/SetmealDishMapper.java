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
     * @description:根据套餐id查询包含的菜品，用户端使用
     * @author: Cvvvv
     * @param: [setmealId]
     * @return: java.util.List<com.sky.entity.SetmealDish>
     */
    @Select("select s.name,s.copies,d.image,d.description " +
            "from setmeal_dish s left join dish d on s.dish_id = d.id " +
            "where s.setmeal_id = #{setmealId}")
    List<DishItemVO> queryDishesBySetMealId(Long setmealId);


    /**
     *
     * @description:添加套餐和菜品关系
     * @author: Cvvvv
     * @param: [setmealDishes]
     * @return: void
     */
    void insertRelationship(List<SetmealDish> setmealDishes);


    /**
     *
     * @description:根据套餐id查询包含的菜品,管理端使用
     * @author: Cvvvv
     * @param: [setmealId]
     * @return: java.util.List<com.sky.entity.SetmealDish>
     */
    @Select("select id,name,price,setmeal_id,dish_id,copies from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> queryDishesBySetMealIdAdmin(Long setmealId);

    /**
     *
     * @description:根据多个套餐id，批量删除套餐与菜品关系表中的数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: void
     */
    void deleteDishsBySetMealIds(List<Long> setMealIds);

}
