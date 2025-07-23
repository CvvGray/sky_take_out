package com.sky.dto;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;

@Data
@Api("新增员工数据传输dto")
public class EmployeeDTO implements Serializable {
    public Integer id;

    public String IdNumber;

    public String name;

    public String phone;

    public String sex;

    public String username;
}