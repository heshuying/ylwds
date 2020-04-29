package com.hailian.ylwmall.controller.admin;

import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.PageUtils;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("/custom")
public class CustomController {

   @Autowired
   private TbUserService userService;


    @GetMapping("/users")
    public String usersPage(HttpServletRequest request) {
        request.setAttribute("path", "users");
        // return "admin/newbee_mall_user";
        return "admin/userManage";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageUtils pageUtil= userService.queryUserList(params);
        return ResultGenerator.genSuccessResult(pageUtil);
    }

    /**
     * 用户禁用与解除禁用(1-正常 4-已锁定)
     */
    @RequestMapping(value = "/users/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable Integer lockStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        userService.updateUserStatus(lockStatus, Arrays.asList(ids));
        return ResultGenerator.genSuccessResult();
    }
}