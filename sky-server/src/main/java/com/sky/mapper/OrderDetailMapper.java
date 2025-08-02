package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {


    /**
     *
     * @description:向订单详情插入数据
     * @author: Cvvvv
     * @param: [orderDetailList]
     * @return: void
     */
    void insertBatch(List<OrderDetail> orderDetailList);

    /**
     *
     * @description:根据订单id查询订单详细信息
     * @author: Cvvvv
     * @param: [orderId]
     * @return: java.util.List<com.sky.entity.OrderDetail>
     */
    @Select("SELECT id, name, image, order_id, dish_id, setmeal_id, dish_flavor, number, amount " +
            "FROM order_detail " +
            "WHERE order_id = #{orderId} " +
            "ORDER BY id ASC")
    List<OrderDetail> queryOrderDetailByOrderId(Long orderId);
}
