package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);



    /**
     * 新增一名员工
     * @param employee
     * @return
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status) " +
            "values " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    @AutoFill(value = OperationType.INSERT)
    void addEmployee(Employee employee);



    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQueryEmployee(EmployeePageQueryDTO employeePageQueryDTO);




    /**
     * 根据id修改员工账号信息
     * @param employee
     * @return
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateEmployeeInformation(Employee employee);


    /**
     * 根据id修改员工信息
     *
     * @param id
     * @return
     */
    @Select("select id, name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status from employee where id=#{id}")
    @AutoFill(value = OperationType.UPDATE)
    Employee queryEmployeeById(Long id);
}
