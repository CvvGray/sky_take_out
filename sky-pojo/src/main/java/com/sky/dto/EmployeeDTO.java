package com.sky.dto;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;

@Data
@Api("新增员工数据传输DTO")
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}