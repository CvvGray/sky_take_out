package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {


    /**
     *
     * @description:向订单中插入数据
     * @author: Cvvvv
     * @param: [order]
     * @return: void
     */
    void addOrder(Orders order);

    /**
     *
     * @description:根据订单号查询当前用户的订单
     * @author: Cvvvv
     * @param: [outTradeNo, userId]
     * @return: com.sky.entity.Orders
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);



    /**
     *
     * @description:
     * @author: Cvvvv
     * @param: [orders]
     * @return: void
     */
    void updateOrderInformation(Orders orders);
}
