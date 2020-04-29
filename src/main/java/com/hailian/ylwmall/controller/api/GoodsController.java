package com.hailian.ylwmall.controller.api;

import com.hailian.ylwmall.entity.TbGoodsInfo;
import io.swagger.annotations.Api;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "商品相关接口", tags = {"商品"})
@RestController
@RequestMapping("/api")
public class GoodsController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods/detail/{goodsId}")
    public Result goodsDetail(@PathVariable("goodsId") Long goodsId) {
        if (goodsId < 1) {
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }

        TbGoodsInfo goods = goodsService.getNewBeeMallGoodsById(goodsId);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(goods);
        return result;
    }

}
