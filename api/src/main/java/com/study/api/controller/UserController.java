package com.study.api.controller;

import com.study.api.entity.Users;
import com.study.api.service.UserService;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @auther lkx
 * @create 2022-04-27 23:08
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Api(value = "提供用户的注册和登录接口",tags = "用户管理")
@CrossOrigin  //前后端跨域问题，前端用jsonp,后端用@CrossOrigin注解
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "username",value = "用户登录账号",required = true),
            @ApiImplicitParam(dataType = "string",name = "password",value = "用户登录密码",defaultValue = "123456",required = true)
    })
    @GetMapping("/login")
    public ResultVO login(@RequestParam("username") String name, @RequestParam(value = "password") String pwd){
        ResultVO vo = userService.checkLogin(name, pwd);
        return vo;
    }
    @ApiOperation("用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "username",value = "用户注册账号",required = true),
            @ApiImplicitParam(dataType = "string",name = "password",value = "用户注册密码",defaultValue = "123456",required = true)
    })
    @PostMapping("/register")
    public ResultVO register(@RequestBody Users users){
        ResultVO resultVO = userService.userRegister(users.getUsername(), users.getPassword());
        return resultVO;
    }

    @ApiOperation("校验token是否过期接口")
    @GetMapping ("/check")
    public ResultVO userTokenCheck(@RequestHeader("token") String token){
        return new ResultVO(ResStatus.OK,"success",null);
    }
}
