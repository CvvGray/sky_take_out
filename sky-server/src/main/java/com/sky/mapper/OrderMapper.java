package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

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

   /**
    *
    * @description:根据状态和下单时间查询订单
    * @author: Cvvvv
    * @param: [status, orderTime]
    * @return: List<Orders>
    */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);


    /**
     *
     * @description:根据订单id查询订单
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.entity.Orders
     */
    @Select("SELECT id, number, status, user_id, address_book_id, order_time, checkout_time, pay_method, pay_status, amount, remark, phone, address, user_name, consignee, cancel_reason, rejection_reason, cancel_time, estimated_delivery_time, delivery_status, delivery_time, pack_amount, tableware_number, tableware_status " +
            "FROM orders " +
            "WHERE id = #{id}")
    Orders queryOrderById(Long id);

    /**
     *
     * @description:查询当前用户所有已完成的订单（status = 5）
     * @author: Cvvvv
     * @param: [orderDto]
     * @return: com.github.pagehelper.Page<com.sky.vo.OrderVO>
     */
    Page<OrderVO> queryOrdersByUserId(OrdersPageQueryDTO orderDto);
}
