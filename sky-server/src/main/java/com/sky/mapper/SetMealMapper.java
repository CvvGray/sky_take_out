package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetMealMapper {


    /**
     *
     * @description:根据setmealDTO中的[id],[name],[status],[categoryId]查询套餐数据，以上参数可有可无
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: java.util.List<com.sky.entity.Setmeal>
     */
    List<Setmeal> querySetMeal(SetmealDTO setmealDTO);


    /**
     *
     * @description:分页查询套餐
     * @author: Cvvvv
     * @param: [setmealPageQueryDTO]
     * @return: com.github.pagehelper.Page
     */
    Page<Setmeal> pageQuerySetMeal(SetmealPageQueryDTO setmealPageQueryDTO);



    /**
     *
     * @description:新增套餐
     * @author: Cvvvv
     * @param: [setmealDTO]
     * @return: void
     */
    @Insert("insert into setmeal(category_id, name, price, description, image, create_time, update_time, create_user, update_user) " +
            "value" +
            "(#{categoryId},#{name},#{price},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @AutoFill(value = OperationType.INSERT)
    void insertSetMeal(Setmeal setmeal);





    /**
     *
     * @description:根据套餐id查询套餐信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.vo.SetmealVO
     */
    @Select("select s.id, s.category_id, s.name, s.price, s.status, s.description, s.image, s.update_time, c.name " +
            "from setmeal s " +
            "left join category c " +
            "on s.category_id = c.id " +
            "where s.id = #{setmealId}")
    SetmealVO getSetMealById(Long setmealId);

    /**
     *
     * @description:根据id批量查询套餐数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: java.util.List<com.sky.entity.Setmeal>
     */
    List<Setmeal> querySetMealByIds(List<Long> ids);


    /**
     *
     * @description:根据id批量删除套餐数据
     * @author: Cvvvv
     * @param: [ids]
     * @return: void
     */
    void deleteSetMealByIds(List<Long> ids);

    /**
     *
     * @description:根据id，修改套餐状态
     * @author: Cvvvv
     * @param: [status, id]
     * @return: void
     */
    @Update("update setmeal set status = #{status} where id = #{id}")
    void updateSetMealStatus(Integer status, Long id);
}
