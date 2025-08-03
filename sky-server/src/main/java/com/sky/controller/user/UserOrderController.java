package com.sky.controller.user;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "C端-订单接口")
public class UserOrderController {



    private static final String  URL =  "http://localhost:8080/notify/paySuccess";


    @Autowired
    private OrderService orderService;


    /**
     *
     * @description:用户下单
     * @author: Cvvvv
     * @param: [ordersSubmitDTO]
     * @return: com.sky.result.Result<com.sky.vo.OrderSubmitVO>
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submitOrder(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单，{}",ordersSubmitDTO.toString());
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
        



    }



    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
//        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = OrderPaymentVO.builder()
                .nonceStr("1670380960")
                .packageStr("prepay_id=wx4f20c9668bfaa259")
                .paySign("qwr234e23123")
                .signType("RSA")
                .timeStamp("4235123123123")
                .build();
        log.info("生成预支付交易单：{}", orderPaymentVO);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        httpClient.execute(httpGet);
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return Result.success(orderPaymentVO);
    }





    /**
     *
     * @description:用户催单
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.result.Result
     */
    @GetMapping("/reminder/{id}")
    @ApiOperation("用户催单")
    public Result userReminder(@PathVariable Long id) {
        log.info("用户催单:{}",id);
        orderService.userReminder(id);
        return Result.success();
    }



    /**
     *
     * @description:根据订单id查询订单详情
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.result.Result
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("订单详情")
    public Result<OrderVO> queryOrderDetail(@PathVariable("id") Long id){
        log.info("订单详情：{}",id);
        OrderVO OrderVO =  orderService.showOrderDetail(id);
        return Result.success(OrderVO);
    }


    /**
     *
     * @description:分页查询历史订单
     * @author: Cvvvv
     * @param: [page, pageSize, status]
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     */

    @GetMapping("/historyOrders")
    @ApiOperation("分页查询历史订单")
    public Result<PageResult<OrderVO>> showHistoryOrders(int page, int pageSize, Integer status) {
        log.info("查询当前用户历史订单");
        PageResult<OrderVO> pageResult = orderService.showHistoryOrders(page, pageSize, status);
        return Result.success(pageResult);
    }


    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result againOrder(@PathVariable Long id){
        log.info("再来一单");
        orderService.againOrder(id);
        return Result.success();
    }


    @PutMapping("/cancel/{id}")
    @ApiOperation("用户取消订单")
    public Result cancelOrder(@PathVariable Long id){
        OrdersCancelDTO ordersCancelDTO = new OrdersCancelDTO();
        ordersCancelDTO.setId(id);
        log.info("用户取消订单");
        orderService.cancelOrder(ordersCancelDTO);
        return Result.success();

    }





}
