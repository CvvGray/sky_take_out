package com.sky.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfiguration {

    /**
     * 配置雪花算法实例
     * 单体项目使用固定workerId和datacenterId
     */
    @Bean
    public Snowflake snowflake() {
        // 参数1: workerId (0-31), 参数2: datacenterId (0-31)
        return IdUtil.getSnowflake(1, 1);
    }
}
