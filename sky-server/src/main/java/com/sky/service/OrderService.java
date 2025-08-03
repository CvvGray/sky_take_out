package com.sky.service;

import cn.hutool.db.sql.Order;
import com.sky.dto.*;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

import java.util.List;

public interface OrderService {

    /**
     *
     * @description:用户下单
     * @author: Cvvvv
     * @param: [ordersSubmitDTO]
     * @return: com.sky.vo.OrderSubmitVO
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);


    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     *
     * @description:用户催单
     * @author: Cvvvv
     * @param: [id]
     * @return: void
     */
    void userReminder(Long id);

    /**
     *
     * @description:根据订单id查询订单详情
     * @author: Cvvvv
     * @param: []
     * @return: com.sky.vo.OrderDetailVO
     */
    OrderVO showOrderDetail(Long orderId);

    /**
     *
     * @description:展示历史订单
     * @author: Cvvvv
     * @param: [page, pageSize, status]
     * @return: com.sky.result.PageResult
     */
    PageResult<OrderVO> showHistoryOrders(int page, int pageSize, Integer status);

    /**
     *
     * @description:再来一单
     * @author: Cvvvv
     * @param: [id]
     * @return: void
     */
    void againOrder(Long id);

    /**
     *
     * @description:取消订单
     * @author: Cvvvv
     * @param: [id]
     * @return: void
     */
    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    /**
     *
     * @description:订单分页查询
     * @author: Cvvvv
     * @param: [pageQueryDTO]
     * @return: com.sky.result.PageResult<java.util.List<com.sky.vo.OrderVO>>
     */
    PageResult<OrderVO> pageQueryOrder(OrdersPageQueryDTO pageQueryDTO);

    /**
     *
     * @description:接单
     * @author: Cvvvv
     * @param: [ordersDTO]
     * @return: void
     */
    void confirmOrder(OrdersDTO ordersDTO);

    /**
     *
     * @description:派送订单
     * @author: Cvvvv
     * @param: [ordersDTO]
     * @return: void
     */
    void deliveryOrder(OrdersDTO ordersDTO);

    /**
     *
     * @description:完成订单
     * @author: Cvvvv
     * @param: [ordersDTO]
     * @return: void
     */
    void completeOrder(OrdersDTO ordersDTO);

    /**
     *
     * @description:拒单
     * @author: Cvvvv
     * @param: [ordersRejectionDTO]
     * @return: void
     */
    void rejectionOrder(OrdersRejectionDTO ordersRejectionDTO);

    /**
     *
     * @description:各个状态的订单数量统计
     * @author: Cvvvv
     * @param: []
     * @return: com.sky.vo.OrderStatisticsVO
     */
    OrderStatisticsVO statisticsOrder();
}
