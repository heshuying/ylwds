package com.hailian.ylwmall.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.controller.vo.NewBeeMallUserVO;
import com.hailian.ylwmall.dto.BuyFormDto;
import com.hailian.ylwmall.dto.OrderFormDto;
import com.hailian.ylwmall.dto.OrderSubmitDto;
import com.hailian.ylwmall.dto.OrderUpdateDto;
import com.hailian.ylwmall.dto.RefundApplyDto;
import com.hailian.ylwmall.dto.RefundDeliveryDto;
import com.hailian.ylwmall.dto.RefundStatusDto;
import com.hailian.ylwmall.dto.ShoppingGoodsUpdateDto;
import com.hailian.ylwmall.entity.TbUserAddr;
import com.hailian.ylwmall.service.NewBeeMallOrderService;
import com.hailian.ylwmall.service.TbOrderOrderinfoService;
import com.hailian.ylwmall.service.TbOrderRefundService;
import com.hailian.ylwmall.service.TbShoppingCartService;
import com.hailian.ylwmall.service.TbUserAddrService;
import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Api(value = "个人中心接口", tags = {"退货"})
@RestController
@RequestMapping("/api/my")
public class RefundController {
    @Autowired
    private TbOrderRefundService orderRefundService;
    @Autowired
    private HttpSession httpSession;


    /**
     * 发起退货
     * @param dto
     * @return
     */
    @ApiOperation(value = "发起退货")
    @PostMapping("/refundApply")
    @ResponseBody
    public Result refundApply(@RequestBody RefundApplyDto dto) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        return orderRefundService.refundApply(user.getUserId(), dto);
    }

    /**
     * 退货单信息查询
     * @param orderId
     * @return
     */
    @ApiOperation(value = "退货单信息查询")
    @GetMapping("/refundInfo")
    @ResponseBody
    public Result refundInfo(@RequestParam Long orderId) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        return orderRefundService.refundInfo(orderId);
    }

    /**
     * 退货提交物流
     * @param dto
     * @return
     */
    @ApiOperation(value = "退货提交物流")
    @PostMapping("/refund/delivery")
    @ResponseBody
    public Result refundDelivery(@RequestBody RefundDeliveryDto dto) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        return orderRefundService.refundDelivery(dto);
    }

    /**
     * 更新退货状态
     * @param dto
     * @return
     */
    @ApiOperation(value = "更新退货状态")
    @PostMapping("/refund/updateStatus")
    @ResponseBody
    public Result updateRefundStatus(@RequestBody RefundStatusDto dto) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        return orderRefundService.updateRefundStatus(dto);
    }

    /**
     * 计算退货金额
     * @param orderGoodsId
     * @return
     */
    @ApiOperation(value = "计算退货金额")
    @GetMapping("/refund/calRefundAmount")
    @ResponseBody
    public Result calRefundAmount(@RequestParam Long orderGoodsId,
            @RequestParam Integer refundNum) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        return orderRefundService.calRefundAmount(orderGoodsId, refundNum);
    }

}
