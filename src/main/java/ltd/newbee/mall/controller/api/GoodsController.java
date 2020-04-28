package ltd.newbee.mall.controller.api;

import io.swagger.annotations.Api;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallGoodsDetailVO;
import ltd.newbee.mall.controller.vo.SearchPageCategoryVO;
import ltd.newbee.mall.entity.TbGoodsInfo;
import ltd.newbee.mall.service.CategoryService;
import ltd.newbee.mall.service.GoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
