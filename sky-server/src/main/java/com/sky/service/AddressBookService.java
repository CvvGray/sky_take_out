package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {


    /**
     *
     * @description:新增地址
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: void
     */
    void addAddressBook(AddressBook addressBook);

    /**
     *
     * @description:查询当前登录用户的所有地址信息
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: java.util.List<com.sky.entity.AddressBook>
     */
    List<AddressBook> showAddressBook(AddressBook addressBook);

    /**
     *
     * @description:根据地址id，修改默认地址为当前地址
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: void
     */
    void updateDefaultAddress(AddressBook addressBook);


    /**
     *
     * @description:根据地址id查询地址信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.entity.AddressBook
     */
    AddressBook getAddressBookById(Long id);

    /**
     *
     * @description:根据id修改地址信息
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: void
     */
    void updateAddressBookById(AddressBook addressBook);

    /**
     *
     * @description:根据地址删除地址信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.entity.AddressBook
     */
    void deleteAddressBookById(Long id);

    /**
     *
     * @description:
     * @author: Cvvvv
     * @param: []
     * @return: com.sky.entity.AddressBook
     */
    AddressBook showDefaultAddress();
}
