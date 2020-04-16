package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.service.TbUserService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageUtils;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class NewBeeMallUserController {

    @Resource
    private NewBeeMallUserService newBeeMallUserService;

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
        if (lockStatus != 1 && lockStatus != 4) {
            return ResultGenerator.genFailResult("操作非法！");
        }
        userService.updateUserStatus(lockStatus, Arrays.asList(ids));
        return ResultGenerator.genSuccessResult();
    }
}