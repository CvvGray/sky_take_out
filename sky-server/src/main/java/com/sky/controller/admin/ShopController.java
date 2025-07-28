package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.exception.ParameterErrorException;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺操作接口")
public class ShopController {

    private static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     *
     * @description:修改店铺营业状态
     * @author: Cvvvv
     * @param: [status]
     * @return: com.sky.result.Result
     */
    @PutMapping("/{status}")
    @ApiOperation("修改营业状态")
    public Result setShopStatus(@PathVariable Integer status) {
        log.info("修改店铺营业状态为：{}",status);
        if (!status.equals(StatusConstant.ENABLE) && !status.equals(StatusConstant.DISABLE) ){
            throw new ParameterErrorException(MessageConstant.PARAMETER_ERROR);
        }
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }


    /**
     *
     * @description:获取店铺营业状态
     * @author: Cvvvv
     * @param: [status]
     * @return: com.sky.result.Result
     */
    @GetMapping("/status")
    @ApiOperation("获取营业状态")
    public Result<Integer> getShopStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺营业状态为：{}",status);
        return Result.success(status);
    }


}
