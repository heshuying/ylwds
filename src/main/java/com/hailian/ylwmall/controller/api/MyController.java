package com.hailian.ylwmall.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.controller.vo.NewBeeMallUserVO;
import com.hailian.ylwmall.dto.ShoppingCartFormDto;
import com.hailian.ylwmall.entity.NewBeeMallShoppingCartItem;
import com.hailian.ylwmall.entity.TbShoppingCart;
import com.hailian.ylwmall.entity.TbUserAddr;
import com.hailian.ylwmall.service.NewBeeMallOrderService;
import com.hailian.ylwmall.service.TbShoppingCartService;
import com.hailian.ylwmall.service.TbUserAddrService;
import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 19012964 on 2020/5/2.
 */
@Api(value = "个人中心接口", tags = {"我的"})
@RestController
@RequestMapping("/api/my")
public class MyController {
    @Autowired
    private TbUserAddrService userAddrService;
    @Autowired
    private TbUserService userService;
    @Autowired
    private NewBeeMallOrderService orderService;
    @Autowired
    private TbShoppingCartService shoppingCartService;
    @Autowired
    private HttpSession httpSession;
    /**
     * 我的订单列表
     * @param params
     * @param request
     * @param httpSession
     * @return
     */
    @ApiOperation(value = "我的订单列表")
    @GetMapping("/orders")
    @ResponseBody
    public Result orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("customerId", user.getUserId());
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(orderService.getMyOrdersForSupplier(pageUtil));
    }

    /**
     * 我的收获地址
     * @param request
     * @param httpSession
     * @return
     */
    @ApiOperation(value = "我的收获地址")
    @GetMapping("/delivery")
    @ResponseBody
    public Result delivery(HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        List<TbUserAddr> userAddrList=userAddrService.list(
                new QueryWrapper<TbUserAddr>()
                        .eq("user_id",user.getUserId())
                        .eq("status",0)
        );
        return ResultGenerator.genSuccessResult(userAddrList);
    }

    @ApiOperation(value = "新增我的收获地址")
    @PostMapping("/delivery/add")
    @ResponseBody
    public Result addDelivery(@RequestBody TbUserAddr userAddr, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        userAddr.setUserId(user.getUserId());
        userAddr.setCreateTime(new Date());
        userAddr.setStatus(0);
        userAddr.setIsDefault(false);
        userAddrService.save(userAddr);
        return ResultGenerator.genSuccessResult();
    }
    @ApiOperation(value = "删除我的收获地址")
    @PostMapping("/delivery/del")
    @ResponseBody
    public Result delDelivery(@RequestBody Long deliveryId, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        TbUserAddr userAddr=userAddrService.getById(deliveryId);
        userAddr.setStatus(1);
        userAddr.setUserId(user.getUserId());
        userAddr.setCreateTime(new Date());
        userAddrService.updateById(userAddr);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "编辑我的收获地址")
    @PostMapping("/delivery/update")
    @ResponseBody
    public Result updateDelivery(@RequestBody TbUserAddr userAddr, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        userAddrService.updateById(userAddr);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "设置默认收获地址")
    @PostMapping("/delivery/default")
    @ResponseBody
    public Result updateDelivery(@RequestBody Long deliveryId, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        TbUserAddr exists=new TbUserAddr();
        exists.setUserId(user.getUserId());
        exists.setIsDefault(false);
        userAddrService.update(exists,new QueryWrapper<TbUserAddr>()
                .eq("user_id", user.getUserId()));

        TbUserAddr defaultAddr=new TbUserAddr();
        defaultAddr.setAddrId(deliveryId);
        defaultAddr.setIsDefault(true);
        userAddrService.updateById(defaultAddr);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "添加购物车")
    @PostMapping("/shoppingCart/add")
    @ResponseBody
    public Result addShoppingCart(@RequestBody ShoppingCartFormDto shoppingCartFormDto) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        shoppingCartFormDto.setUserId(user.getUserId());
        return shoppingCartService.addShoppingCart(shoppingCartFormDto);
    }
}
