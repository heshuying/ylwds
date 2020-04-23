package ltd.newbee.mall.controller.api;

import ltd.newbee.mall.dto.ProfileDto;
import ltd.newbee.mall.dto.RegisterFirstDto;
import ltd.newbee.mall.entity.TbUserProfile;
import ltd.newbee.mall.service.TbModuleService;
import ltd.newbee.mall.service.TbUserService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
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
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private TbUserService userService;
    @Autowired
    private TbModuleService moduleService;

    @PostMapping("/register/first")
    @ResponseBody
    public Result registFirst(@RequestBody RegisterFirstDto registerFirstDto){

        return userService.register(registerFirstDto);
    }

    @GetMapping("/profile/info")
    @ResponseBody
    public Result profileInfo(@RequestParam Long userId){
        return userService.getProfile(userId);
    }

    @PostMapping("/profile/update")
    @ResponseBody
    public Result updateProfile(@RequestBody ProfileDto dto){
        return userService.updateUserProfile(dto);
    }

    @GetMapping("/banners")
    @ResponseBody
    public Result banners(){
        return moduleService.getBanner();
    }

    @GetMapping("/modules")
    @ResponseBody
    public Result modules(){
        return moduleService.getModules();
    }

}
