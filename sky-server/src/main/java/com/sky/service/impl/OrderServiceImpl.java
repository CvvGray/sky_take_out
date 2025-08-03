package com.sky.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.db.sql.Order;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.NoticeConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.BaseException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Snowflake snowflake;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     *
     * @description:用户下单
     * @author: Cvvvv
     * @param: [ordersSubmitDTO]
     * @return: com.sky.vo.OrderSubmitVO
     */

    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        //异常情况的处理（收货地址为空、超出配送范围、购物车为空）
        AddressBook addressBook = addressBookMapper.queryAddressById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //查询当前用户的购物车数据
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.queryShoppingCartByUserId(userId);
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }


        //构造订单数据
        Orders order = Orders.builder()
                .number(String.valueOf(generatePureNumericOrderId()))
                .phone(addressBook.getPhone())
                .address(addressBook.getDetail())
                .consignee(addressBook.getConsignee())
                .userId(userId)
                .status(Orders.PENDING_PAYMENT)
                .payStatus(Orders.PAID)
                .orderTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(ordersSubmitDTO,order);

        //向订单表插入1条数据
        orderMapper.addOrder(order);

        //订单明细数据
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetailList.add(orderDetail);
        }

        //向明细表插入n条数据
        orderDetailMapper.insertBatch(orderDetailList);

        //清理购物车中的数据
        ShoppingCart shoppingCart = ShoppingCart.builder().userId(userId).build();
        shoppingCartMapper.deleteShoppingCart(shoppingCart);

        //封装返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderAmount(order.getAmount())
                .orderTime(order.getOrderTime())
                .build();

        return orderSubmitVO;
    }


    /**
     * 生成纯数字订单号（18位）
     */
    public long generatePureNumericOrderId() {
        return snowflake.nextId();
    }


    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();

        // 根据订单号查询当前用户的订单
        Orders ordersDB = orderMapper.getByNumberAndUserId(outTradeNo, userId);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.updateOrderInformation(orders);



        Map map = new HashMap();
        map.put("type", NoticeConstant.ORDERING_NOTICE);//消息类型
        map.put("orderId", orders.getId());
        map.put("content", "订单号：" + outTradeNo);

        //通过WebSocket实现来单提醒，向客户端浏览器推送消息
        webSocketServer.sendToAllClient(JSON.toJSONString(map));

    }


    /**
     *
     * @description:用户催单
     * @author: Cvvvv
     * @param: [id]
     * @return: void
     */
    @Override
    public void userReminder(Long id) {
        // 查询订单是否存在
        Orders order = orderMapper.queryOrderById(id);
        if (order == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        Map map = new HashMap<>();
        map.put("type", NoticeConstant.HASTEN_NOTICE);
        map.put("orderId", id);
        map.put("content", "订单号：" + order.getNumber());
        webSocketServer.sendToAllClient(JSON.toJSONString(map));

    }


    /**
     *
     * @description:根据订单id查询订单详情
     * @author: Cvvvv
     * @param: []
     * @return: com.sky.vo.OrderDetailVO
     */
    @Override
    public OrderVO showOrderDetail(Long orderId) {
        // 1. 查询订单主信息
        Orders order = orderMapper.queryOrderById(orderId);
        if (order == null) {
            throw new OrderBusinessException("订单不存在");
        }

        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);


        // 2. 查询订单明细
        List<OrderDetail> orderDetails = orderDetailMapper.queryOrderDetailByOrderId(orderId);
        orderVO.setOrderDetailList(orderDetails);

        return orderVO;
    }

    /**
     *
     * @description:展示历史订单
     * @author: Cvvvv
     * @param: [page, pageSize, status]
     * @return: com.sky.result.PageResult<com.sky.vo.OrderVO>
     */
    @Override
    public PageResult<OrderVO> showHistoryOrders(int page, int pageSize, Integer status) {
        PageHelper.startPage(page, pageSize);

        OrdersPageQueryDTO orderDto = new OrdersPageQueryDTO();
        orderDto.setStatus(status);
        orderDto.setUserId(BaseContext.getCurrentId());

        Page<OrderVO> pageOrders =  orderMapper.queryOrdersByUserId(orderDto);

        long total = pageOrders.getTotal();
        List<OrderVO> records = pageOrders.getResult();
        if (records != null && records.size() > 0) {
            for (OrderVO orderVO : records) {
                List<OrderDetail> orderDetails = orderDetailMapper.queryOrderDetailByOrderId(orderVO.getId());
                orderVO.setOrderDetailList(orderDetails);
            }

        }

        return new PageResult<>(total, records);
    }


    /**
     *
     * @description:再来一单
     * @author: Cvvvv
     * @param: [id]
     * @return: void
     */
    @Override
    public void againOrder(Long id) {
        Orders order = orderMapper.queryOrderById(id);
        if (order == null) {
            throw new BaseException("订单异常");
        }
        List<OrderDetail> orderDetails = orderDetailMapper.queryOrderDetailByOrderId(order.getId());

        // 查询当前用户id
        Long userId = BaseContext.getCurrentId();

        // 将订单详情对象转换为购物车对象
        List<ShoppingCart> shoppingCartList = orderDetails.stream().map(x -> {
            ShoppingCart shoppingCart = new ShoppingCart();

            // 将原订单详情里面的菜品信息重新复制到购物车对象中
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;

        }).collect(Collectors.toList());

        // 将购物车对象批量添加到数据库
        shoppingCartMapper.insertBatch(shoppingCartList);


    }


    /**
     *
     * @description:取消订单
     * @author: Cvvvv
     * @param: [id]
     * @return: void
     */
    @Override
    public void cancelOrder(OrdersCancelDTO ordersCancelDTO) {
        Orders order = orderMapper.queryOrderById(ordersCancelDTO.getId());
        if (order == null) {
            throw new BaseException("未知错误");
        }

        order.setStatus(Orders.CANCELLED);
        // 支付状态
        Integer payStatus = order.getStatus();
        if (payStatus == 1) {
            // 用户已支付，需要退款
            order.setCancelTime(LocalDateTime.now());
            order.setCancelReason("用户需要退款");
        }else{
            // 用户未支付，需要退款
            order.setStatus(Orders.CANCELLED);
            order.setCancelTime(LocalDateTime.now());
            order.setCancelReason(ordersCancelDTO.getCancelReason());
        }

        orderMapper.updateOrderInformation(order);
    }


   /**
    *
    * @description:订单分页查询
    * @author: Cvvvv
    * @param: [pageQueryDTO]
    * @return: com.sky.result.PageResult<com.sky.vo.OrderVO>
    */
    @Override
    public PageResult<OrderVO> pageQueryOrder(OrdersPageQueryDTO pageQueryDTO) {
        PageHelper.startPage(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        pageQueryDTO.setUserId(BaseContext.getCurrentId());

        Page<OrderVO> orderList = orderMapper.queryOrderByCondition(pageQueryDTO);

        long total = orderList.getTotal();
        List<OrderVO> records = orderList.getResult();

        return new PageResult<>(total, records);
    }


    /**
     *
     * @description:接单
     * @author: Cvvvv
     * @param: [ordersDTO]
     * @return: void
     */
    @Override
    public void confirmOrder(OrdersDTO ordersDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.queryOrderById(ordersDTO.getId());

        // 订单只有存在且状态为2（待接单）才可以接单
        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersDTO, orders);
        orders.setStatus(Orders.CONFIRMED);
        orderMapper.updateOrderInformation(orders);

    }


    /**
     *
     * @description:派送订单
     * @author: Cvvvv
     * @param: [ordersDTO]
     * @return: void
     */
    @Override
    public void deliveryOrder(OrdersDTO ordersDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.queryOrderById(ordersDTO.getId());

        // 订单只有存在且状态为3（已接单）才可以派送订单
        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersDTO, orders);
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);
        orderMapper.updateOrderInformation(orders);
    }

    /**
     *
     * @description:完成订单
     * @author: Cvvvv
     * @param: [ordersDTO]
     * @return: void
     */
    @Override
    public void completeOrder(OrdersDTO ordersDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.queryOrderById(ordersDTO.getId());

        // 订单只有存在且状态为4（派送中）才可以完成订单
        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.DELIVERY_IN_PROGRESS)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersDTO, orders);
        orders.setStatus(Orders.COMPLETED);
        orderMapper.updateOrderInformation(orders);
    }


    /**
     *
     * @description:拒单
     * @author: Cvvvv
     * @param: [ordersRejectionDTO]
     * @return: void
     */
    @Override
    public void rejectionOrder(OrdersRejectionDTO ordersRejectionDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.queryOrderById(ordersRejectionDTO.getId());

        // 订单只有存在且状态为2（待接单）才可以拒单
        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 拒单需要退款，根据订单id更新订单状态、拒单原因、取消时间
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        orders.setCancelTime(LocalDateTime.now());

        orderMapper.updateOrderInformation(orders);

    }
}
