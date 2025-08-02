package com.sky.service.impl;

import com.sky.constant.AddressConstant;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;


    /**
     *
     * @description:新增地址
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: void
     */
    @Override
    public void addAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        // 1. 查询用户现有地址
        List<AddressBook> existingAddresses = addressBookMapper.queryAddressByUserId(addressBook);

        // 2. 如果是首个地址，强制设为默认
        if (existingAddresses == null || existingAddresses.isEmpty()) {
            addressBook.setIsDefault(AddressConstant.DEFAULT);
        }
        // 3. 用户未设置时，自动设为非默认
        else if (addressBook.getIsDefault() == null) {
            addressBook.setIsDefault(AddressConstant.NOT_DEFAULT);
        }
        // 4. 插入新地址
        addressBookMapper.addAnewAddressBoot(addressBook);
    }


    /**
     *
     * @description:查询当前登录用户的所有地址信息
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: java.util.List<com.sky.entity.AddressBook>
     */
    @Override
    public List<AddressBook> showAddressBook(AddressBook addressBook) {
        List<AddressBook> addressBooks = addressBookMapper.queryAddressByUserId(addressBook);
        return addressBooks;
    }



    /**
     *
     * @description:根据地址id，修改默认地址为当前地址
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: void
     */
    @Override
    public void updateDefaultAddress(AddressBook addressBook) {
        //先查找默认地址，将原先的默认地址设为非默认，
        AddressBook addressBookDefault = addressBookMapper.queryDefaultAddress(BaseContext.getCurrentId());
        addressBookDefault.setIsDefault(AddressConstant.NOT_DEFAULT);
        addressBookMapper.updateAddressInformation(addressBookDefault);


        //再将现在的地址设为默认地址
        addressBook.setIsDefault(AddressConstant.DEFAULT);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateAddressInformation(addressBook);

    }


    /**
     *
     * @description:根据地址id查询地址信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.entity.AddressBook
     */
    @Override
    public AddressBook getAddressBookById(Long id) {
        AddressBook addressBook = addressBookMapper.queryAddressById(id);
        return addressBook;
    }

    /**
     *
     * @description:根据id修改地址信息
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: void
     */
    @Override
    public void updateAddressBookById(AddressBook addressBook) {
        addressBookMapper.updateAddressInformation(addressBook);
    }


    /**
     *
     * @description:根据地址删除地址信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.entity.AddressBook
     */
    @Override
    public void deleteAddressBookById(Long id) {
        addressBookMapper.deleteAddressBookById(id);
    }

    /**
     *
     * @description:查询默认地址
     * @author: Cvvvv
     * @param: []
     * @return: com.sky.entity.AddressBook
     */
    @Override
    public AddressBook showDefaultAddress() {
        AddressBook addressBook = addressBookMapper.queryDefaultAddress(BaseContext.getCurrentId());
        return addressBook;
    }
}
