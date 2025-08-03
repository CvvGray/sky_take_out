package com.sky.controller.admin;

import cn.hutool.db.sql.Order;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "商家订单相关接口")
public class AdminOrderController {


    @Autowired
    private OrderService orderService;


    /**
     *
     * @description:订单分页查询
     * @author: Cvvvv
     * @param: [pageQueryDTO]
     * @return: com.sky.result.Result<com.sky.result.PageResult<java.util.List<com.sky.vo.OrderVO>>>
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("订单分页查询")
    public Result<PageResult<OrderVO>> pageQueryOrder(OrdersPageQueryDTO pageQueryDTO){
        log.info("订单分页查询");
        PageResult<OrderVO> pagesOrders = orderService.pageQueryOrder(pageQueryDTO);
        return Result.success(pagesOrders);
    }





    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> pageQueryOrder(@PathVariable("id") Long id){
        log.info("查询订单详情");
        OrderVO orderVO = orderService.showOrderDetail(id);
        return Result.success(orderVO);
    }



    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirmOrder(@RequestBody OrdersDTO ordersDTO){
        log.info("接单" + ordersDTO.getId());
        orderService.confirmOrder(ordersDTO);
        return Result.success();

    }




    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result deliveryOrder(@PathVariable("id") Long id){
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setId(id);
        log.info("派送订单" + ordersDTO.getId());
        orderService.deliveryOrder(ordersDTO);
        return Result.success();

    }



    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result completeOrder(@PathVariable("id") Long id){
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setId(id);
        log.info("完成订单" + ordersDTO.getId());
        orderService.completeOrder(ordersDTO);
        return Result.success();

    }


   /**
    *
    * @description:拒单
    * @author: Cvvvv
    * @param: [ordersRejectionDTO]
    * @return: com.sky.result.Result<java.lang.String>
    */
    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result<String> rejectionOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        log.info("拒单" + ordersRejectionDTO.getId());
        orderService.rejectionOrder(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result<String> cancelOrder(@RequestBody OrdersCancelDTO OrdersCancelDTO){
        log.info("取消订单" + OrdersCancelDTO.getId());
        orderService.cancelOrder(OrdersCancelDTO);
        return Result.success();
    }





















}
