package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.UserLoginVO;

public interface UserService {

    /**
     *
     * @description:小程序用户使用微信登录
     * @author: Cvvvv
     * @param: [userLoginDTO]
     * @return: com.sky.vo.UserLoginVO
     */
    User loginByWechat(UserLoginDTO userLoginDTO);
}
