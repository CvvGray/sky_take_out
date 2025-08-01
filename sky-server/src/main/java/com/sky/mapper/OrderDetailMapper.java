package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

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
}
