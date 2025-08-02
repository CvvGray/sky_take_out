package com.sky.controller.user;

import com.alibaba.druid.support.json.JSONUtils;
import com.sky.service.OrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


/**
 * 支付回调相关接口
 */
@RestController
@RequestMapping("/notify")
@Slf4j
@Api(tags = "支付功功能接口")
public class PayNotifyController {
    @Autowired
    private OrderService orderService;

    /**
     * 支付成功回调
     *
     * @param request
     */
//    @RequestMapping("/paySuccess")
    @GetMapping("/paySuccess")
    public void paySuccessNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //给微信响应
        responseToWeixin(response);
    }


    /**
     * 给微信响应
     * @param response
     */
    private void responseToWeixin(HttpServletResponse response) throws Exception{
        response.setStatus(200);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("code", "SUCCESS");
        map.put("message", "SUCCESS");
        response.setHeader("Content-type", ContentType.APPLICATION_JSON.toString());
        response.getOutputStream().write(JSONUtils.toJSONString(map).getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
    }

}
