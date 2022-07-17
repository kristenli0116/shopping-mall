package com.study.api.controller;

import com.study.api.service.UserAddressService;
import com.study.api.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther lkx
 * @create 2022-05-08 14:22
 * @Description:
 */
@RestController
@CrossOrigin
@Api(value = "提供收获地址相关接口",tags = "收货地址管理")
@RequestMapping("/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @ApiImplicitParam(dataType = "int",name = "userId",value = "用户ID",required = true)
    @GetMapping("list")
    public ResultVO listAddress(Integer userId, @RequestHeader("token") String token){
        ResultVO resultVO = userAddressService.listAddressByUid(userId);
        return resultVO;
    }
}
