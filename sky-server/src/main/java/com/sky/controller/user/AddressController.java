package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ResultExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "地址簿接口")
public class AddressController {


    @Autowired
    private AddressBookService addressBookService;


    @PostMapping
    @ApiOperation("新增地址")
    public Result addAddressBook(@RequestBody AddressBook addressBook) {
        log.info("新增地址：{}", addressBook.toString());
        addressBookService.addAddressBook(addressBook);
        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> showAddressBook() {
        log.info("查询当前登录用户的所有地址信息");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.showAddressBook(addressBook);
        return Result.success(list);
    }


    /**
     *
     * @description:根据地址id，修改默认地址为当前地址
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: com.sky.result.Result
     */
    @PutMapping("/default")
    @ApiOperation("修改默认地址")
    public Result updateDefaultAddress(@RequestBody AddressBook addressBook) {
        log.info("根据id修改默认地址，{}", addressBook.getId());
        addressBookService.updateDefaultAddress(addressBook);
        return Result.success();
    }



    /**
     *
     * @description:根据地址id查询地址信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.result.Result<com.sky.entity.AddressBook>
     */
    @GetMapping("/{id}")
    @ApiOperation("根据地址id查询地址信息")
    public Result<AddressBook> getAddressBookById(@PathVariable("id") Long id) {
        log.info("根据地址id查询地址信息,{}",id);
        AddressBook addressBook = addressBookService.getAddressBookById(id);
        return Result.success(addressBook);
    }

    /**
     *
     * @description:根据id修改地址信息
     * @author: Cvvvv
     * @param: [addressBook]
     * @return: com.sky.result.Result
     */
    @PutMapping
    @ApiOperation("修改地址信息")
    public Result updateAddressBookById(@RequestBody AddressBook addressBook) {
        log.info("根据id修改地址信息，{}", addressBook.getId());
        addressBookService.updateAddressBookById(addressBook);
        return Result.success();
    }



    /**
     *
     * @description:根据地址删除地址信息
     * @author: Cvvvv
     * @param: [id]
     * @return: com.sky.result.Result
     */
    @DeleteMapping
    @ApiOperation("根据地址删除地址信息")
    public Result deleteAddressBookById(Long id) {
        log.info("根据地址删除地址信息,{}",id);
        addressBookService.deleteAddressBookById(id);
        return Result.success();
    }


    /**
     *
     * @description:查询默认地址
     * @author: Cvvvv
     * @param: []
     * @return: com.sky.result.Result<com.sky.entity.AddressBook>
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> showDefaultAddress() {
        log.info("查询当前默认地址");
        AddressBook addressBook = addressBookService.showDefaultAddress();
        return Result.success(addressBook);
    }




}
