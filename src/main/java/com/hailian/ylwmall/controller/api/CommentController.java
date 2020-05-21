package com.hailian.ylwmall.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.controller.vo.NewBeeMallUserVO;
import com.hailian.ylwmall.dto.CommentReq;
import com.hailian.ylwmall.entity.TbGoodsComment;
import com.hailian.ylwmall.entity.TbOrderGoodinfo;
import com.hailian.ylwmall.service.TbGoodsCommentService;
import com.hailian.ylwmall.service.TbOrderGoodinfoService;
import com.hailian.ylwmall.util.CommonUtil;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(value = "评价相关接口", tags = {"评价相关接口"})
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    TbGoodsCommentService commentService;
    @Autowired
    TbOrderGoodinfoService orderGoodinfoService;

    /**
     * 添加评价
     * @param reqBean
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody CommentReq reqBean, HttpServletRequest request){
        NewBeeMallUserVO user = (NewBeeMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
        if(user == null){
            return ResultGenerator.genFailResult("用户未登录");
        }

        if(reqBean == null || reqBean.getGoodsId() == null || reqBean.getOrderId() == null
                || reqBean.getScore() == null || StringUtils.isBlank(reqBean.getContent())){
            return ResultGenerator.genFailResult("参数异常！");
        }

        List<TbGoodsComment> list = commentService.list(new QueryWrapper<TbGoodsComment>()
                .eq("order_id", reqBean.getOrderId())
                .eq("goods_id", reqBean.getGoodsId()).eq("user_id", user.getUserId()));
        if(list != null && !list.isEmpty()){
            return ResultGenerator.genFailResult("您已评价过");
        }

        TbGoodsComment comment = CommonUtil.convertBean(reqBean, TbGoodsComment.class);
        comment.setUserId(user.getUserId());
        comment.setUserName(user.getLoginName());
        comment.setIsAnonymous(0);
        comment.setIsAuto(1);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        commentService.saveOrUpdate(comment);

        // 更新为已评价
        TbOrderGoodinfo orderGoodInfo = new TbOrderGoodinfo();
        orderGoodInfo.setHasComment(true);
        orderGoodinfoService.update(orderGoodInfo, new QueryWrapper<TbOrderGoodinfo>()
                .eq("order_id", reqBean.getOrderId()).eq("good_id", reqBean.getGoodsId()));

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 评价列表
     * @return
     */
    @GetMapping("/list")
    public Result getModuleDetails(@RequestParam Map<String, Object> params){
        if (org.springframework.util.StringUtils.isEmpty(params.get("page"))
                || org.springframework.util.StringUtils.isEmpty(params.get("limit"))
                || org.springframework.util.StringUtils.isEmpty(params.get("goodsId"))){
            return ResultGenerator.genFailResult("参数异常！");
        }

        Integer scoreAvg = commentService.getScore((String) params.get("goodsId"));
        Page<TbGoodsComment> page = new Page<>(Long.parseLong((String)params.get("page")), Long.parseLong((String)params.get("limit")));
        IPage resultData = commentService.page(page, new QueryWrapper<TbGoodsComment>()
                .eq("goods_id", params.get("goodsId"))
                .orderByDesc("id"));
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(resultData));
        jsonObject.put("scoreAvg", scoreAvg);
        return ResultGenerator.genSuccessResult(jsonObject);
    }
}
