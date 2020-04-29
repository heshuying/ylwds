package com.hailian.ylwmall.controller.api;

import com.hailian.ylwmall.dto.ProfileDto;
import com.hailian.ylwmall.dto.RegisterFirstDto;
import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 19012964 on 2020/4/15.
 */
@Api(value = "用户管理相关接口", tags = {"用户管理"})
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private TbUserService userService;

    @ApiOperation(value = "注册")
    @PostMapping("/register/first")
    @ResponseBody
    public Result registFirst(@RequestBody RegisterFirstDto registerFirstDto){

        return userService.register(registerFirstDto);
    }

    @ApiOperation(value = "获取用户注册信息")
    @GetMapping("/profile/info")
    @ResponseBody
    public Result profileInfo(@RequestParam Long userId){
        return userService.getProfile(userId);
    }

    @ApiOperation(value = "用户注册信息更新")
    @PostMapping("/profile/update")
    @ResponseBody
    public Result updateProfile(@RequestBody ProfileDto dto){
        return userService.updateUserProfile(dto);
    }


}
