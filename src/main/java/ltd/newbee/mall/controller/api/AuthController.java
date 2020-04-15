package ltd.newbee.mall.controller.api;

import ltd.newbee.mall.dto.RegisterFirstDto;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 19012964 on 2020/4/15.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/register/first")
    @ResponseBody
    public Result registFirst(@RequestBody RegisterFirstDto registerFirstDto){

        return ResultGenerator.genSuccessResult();
    }
}
