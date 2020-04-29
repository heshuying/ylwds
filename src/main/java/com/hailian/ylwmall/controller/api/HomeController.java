package com.hailian.ylwmall.controller.api;

import com.hailian.ylwmall.service.TbModuleService;
import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 19012964 on 2020/4/15.
 */
@Api(value = "首页相关接口", tags = {"首页"})
@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private TbUserService userService;
    @Autowired
    private TbModuleService moduleService;

    @ApiOperation(value = "获取banner图")
    @GetMapping("/banners")
    @ResponseBody
    public Result banners(){
        return moduleService.getBanner();
    }

    @ApiOperation(value = "获取专区")
    @GetMapping("/modules")
    @ResponseBody
    public Result modules(){
        return moduleService.getModules();
    }

}
