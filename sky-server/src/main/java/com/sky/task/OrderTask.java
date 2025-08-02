package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     *
     * @description:处理超时订单
     * @author: Cvvvv
     * @param: []
     * @return: void
     */
    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void orderTimeoutHanding() {
        //TODO 此方法暂时有问题，
        log.info("处理超时订单kkkkkkkkkkkkkkkk");
        LocalDateTime orderTimeout = LocalDateTime.now().minusMinutes(15);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, orderTimeout);
        if(ordersList != null && ordersList.size() > 0){
            ordersList.forEach(order -> {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("支付超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.updateOrderInformation(order);
            });
        }

    }

    /**
     *
     * @description:处理一直处于派送中的订单
     * @author: Cvvvv
     * @param: []
     * @return: void
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder(){
        LocalDateTime time = LocalDateTime.now().minusMinutes(60);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);

        if(ordersList != null && ordersList.size() > 0){
            ordersList.forEach(order -> {
                order.setStatus(Orders.COMPLETED);
                orderMapper.updateOrderInformation(order);
            });
        }
    }


}
