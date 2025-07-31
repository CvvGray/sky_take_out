package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    /**
     *
     * @description:添加新地址
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: void
     */
    @Insert("insert into address_book(user_id,consignee,sex,phone,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default) " +
            "values" +
            "( #{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void addAnewAddressBoot(AddressBook addressBook);


    /**
     *
     * @description:查询当前用户所有的地址
     * @author: Cvvvv
     * @param: [userId]
     * @return: java.util.List<com.sky.entity.AddressBook>
     */

    @Select("select id,user_id,consignee,sex,phone,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default " +
            "from address_book " +
            "where user_id = #{userId}")
    List<AddressBook> queryAddressByUserId(AddressBook addressBook);

    /**
     *
     * @description:查找默认地址
     * @author: Cvvvv
     * @param: []
     * @return: com.sky.entity.AddressBook
     */
    @Select("select id,user_id,consignee,sex,phone,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default " +
            "from address_book " +
            "where user_id = #{userId} and is_default = 1")
    AddressBook queryDefaultAddress(Long userId);

    /**
     *
     * @description:根据地址id查询地址信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.entity.AddressBook
     */
    @Select("select id,user_id,consignee,sex,phone,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default " +
            "from address_book " +
            "where id = #{id}")
    AddressBook queryAddressById(Long id);

    /**
     *
     * @description:修改地址信息
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: void
     */
    void updateAddressInformation(AddressBook addressBook);

    /**
     *
     * @description:根据地址删除地址信息
     * @author: Cvvvv
     * @param: [id]
     * @return: void
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteAddressBookById(Long id);
}
