package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工登出")
    public Result<String> logout() {
        return Result.success();
    }



    /**
     * 新增员工
     * @return
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result<String> addemployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}",employeeDTO);
        employeeService.addeEployee(employeeDTO);
        return Result.success();
    }


    /**
     * 分页查询
     * @param    employeePageQueryDTO 中如果有name，则模糊查询带有name的员工。如果没有name,则查询所有员工
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询员工")
    public Result<PageResult> pageQueryEmployee(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询：{}",employeePageQueryDTO);
        PageResult pageResult =  employeeService.pageQueryEmployee(employeePageQueryDTO);

        return Result.success(pageResult);
    }


    /**
     * 修改员工账号状态
     * @param    status,id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改员工账号状态")
    public Result<String> updateEmployeeStatus(@PathVariable Integer status,Long id){
        log.info("修改员工账号状态,{},{}",status,id);
        employeeService.updateEmployeeStatus(status,id);
        return Result.success();
    }


    /**
     * 根据id查询员工信息
     * @param   id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询员工信息")
    public Result<Employee> queryEmployeeById(@PathVariable Long id){
        log.info("根据ID查询员工信息,{}",id);
        Employee employee = employeeService.queryEmployeeById(id);
        return Result.success(employee);
    }

    /**
     * 根据id修改员工信息
     * @param   employeeDTO
     * @return
     */

    //TODO 因为前端传输的id为null,暂时使用身份证代替，后买你记得修改xml文件
    @PutMapping
    @ApiOperation("根据ID修改员工信息")
    public Result updataEmployeeById(@RequestBody EmployeeDTO employeeDTO){
        log.info("根据ID修改员工信息,{}",employeeDTO);
        employeeService.updateEmployeeInformation(employeeDTO);
        return Result.success();
    }

}
