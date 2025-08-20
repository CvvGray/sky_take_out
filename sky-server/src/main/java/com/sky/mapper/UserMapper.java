package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    /**
     *
     * @description:根据openid查询数据库
     * @author: Cvvvv
     * @param: [openid]
     * @return: com.sky.entity.User
     */
    @Select("select id,openid,name,phone,sex,id_number,avatar,create_time from user where openid = #{openid}")
    User getUserOpenid(String openid);

    /**
     *
     * @description:新增一个用户
     * @author: Cvvvv
     * @param: [user]
     * @return: void
     */
    void addAUser(User user);


    /**
     *
     * @description:用户数量统计
     * @author: Cvvvv
     * @param: [beginTime, endTime]
     * @return: java.lang.Integer
     */
    Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime);
}
