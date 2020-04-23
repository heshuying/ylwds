package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.GoodsStatusEnum;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.dto.GoodsStatusUpdateReqDTO;
import ltd.newbee.mall.dto.UserListDto;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.TbGoodsInfo;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.GoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 商品管理
 */
@Controller
@RequestMapping("/admin")
public class AdminGoodsController {

    @Resource
    private GoodsService newBeeMallGoodsService;
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        //查询所有的一级分类
        List<UserListDto> companyList = newBeeMallGoodsService.queryCompanyNameList();
        List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        Map<String,String> goodsStatus = new HashMap<>();
        goodsStatus.put("仓库中", "1,6");
        goodsStatus.put("审核中", "2");
        goodsStatus.put("销售中", "3");
        goodsStatus.put("下架中", "4,5");

        request.setAttribute("companyList", companyList);
        request.setAttribute("firstLevelCategories", firstLevelCategories);
        request.setAttribute("goodsStatus", goodsStatus);
        request.setAttribute("path", "newbee_mall_goods");
        // return "admin/newbee_mall_goods";
        return "admin/platGoodsManage";
        // return "admin/busiGoodsManage";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/busiGoodsAddEdit";
            }
        }
        return "error/error_5xx";
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        TbGoodsInfo newBeeMallGoods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        if (newBeeMallGoods == null) {
            return "error/error_400";
        }
        if (newBeeMallGoods.getGoodsCategoryId() > 0) {
            if (newBeeMallGoods.getGoodsCategoryId() != null || newBeeMallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = newBeeMallCategoryService.getGoodsCategoryById(newBeeMallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = newBeeMallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firestCategory = newBeeMallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (newBeeMallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("goods", newBeeMallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/busiGoodsAddEdit";
    }

    /**
     * tab: waiton, waitdown, selling, havedown
     * tab=categoryId=15&goodsStatus=1,2&companyName=莫某公司&limit=20&page=1&sidx=saleTotal&order=asc
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String goodsStatus = (String)params.get("goodsStatus");
        if(!StringUtils.isEmpty(goodsStatus)){
            params.put("statusList", Arrays.asList(goodsStatus.split(",")));
        }
        String tab = (String)params.get("tab");
        if(!StringUtils.isEmpty(tab)){
            if("waitOn".equalsIgnoreCase(tab)){
                params.put("statusList", Arrays.asList(GoodsStatusEnum.AUDITTING.getGoodsStatus()));
            }else if("waitDown".equalsIgnoreCase(tab)){
                params.put("statusList", Arrays.asList(GoodsStatusEnum.SELLING_OFF_REQUEST.getGoodsStatus(), GoodsStatusEnum.SELLING_OFF_FRONT.getGoodsStatus()));
            }else if("selling".equalsIgnoreCase(tab)){
                params.put("statusList", Arrays.asList(GoodsStatusEnum.SELLING.getGoodsStatus(),GoodsStatusEnum.SELLING_OFF_REQUEST.getGoodsStatus(), GoodsStatusEnum.SELLING_OFF_FRONT.getGoodsStatus()));
            }else if("havedown".equalsIgnoreCase(tab)){
                params.put("statusList", Arrays.asList(GoodsStatusEnum.OFF_INSTORE.getGoodsStatus()));
            }
        }
        if(!StringUtils.isEmpty(params.get("categoryId"))){
            params.put("categoryIdList", newBeeMallCategoryService.getThirdLevelByFirstLevelId(Long.valueOf((String)params.get("categoryId"))));
        }

        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil));
    }

    /**
     * 选择暂存仓库 goodsSellStatus=1，选择申请上架 goodsSellStatus=2
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody TbGoodsInfo newBeeMallGoods) {
        if (StringUtils.isEmpty(newBeeMallGoods.getGoodsName())
                || Objects.isNull(newBeeMallGoods.getOriginalPrice())
                || Objects.isNull(newBeeMallGoods.getSellingPrice())
                || Objects.isNull(newBeeMallGoods.getGoodsCategoryId())
                || Objects.isNull(newBeeMallGoods.getStockNum())
                || Objects.isNull(newBeeMallGoods.getGoodsSellStatus())
//                || StringUtils.isEmpty(newBeeMallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(newBeeMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 选择暂存仓库 goodsSellStatus=1，选择申请上架 goodsSellStatus=2
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody TbGoodsInfo newBeeMallGoods) {
        if (Objects.isNull(newBeeMallGoods.getGoodsId())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(newBeeMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        TbGoodsInfo goods = newBeeMallGoodsService.getNewBeeMallGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result statusUpdate(@RequestBody GoodsStatusUpdateReqDTO requestBean, @PathVariable("sellStatus") Integer sellStatus) {
        if (requestBean.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (!sellStatus.equals(GoodsStatusEnum.INSTORE.getGoodsStatus()) && !sellStatus.equals(GoodsStatusEnum.AUDITTING.getGoodsStatus())
                && !sellStatus.equals(GoodsStatusEnum.SELLING.getGoodsStatus()) && !sellStatus.equals(GoodsStatusEnum.SELLING_OFF_REQUEST.getGoodsStatus())
                && !sellStatus.equals(GoodsStatusEnum.SELLING_OFF_FRONT.getGoodsStatus()) && !sellStatus.equals(GoodsStatusEnum.OFF_INSTORE.getGoodsStatus())) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (newBeeMallGoodsService.batchUpdateSellStatus(requestBean, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    /**
     * 更新商品价格
     */
    @PostMapping(value = "/goods/updatePrice")
    @ResponseBody
    public Result updatePrice(@RequestBody TbGoodsInfo reqBean) {
        if (reqBean == null || reqBean.getGoodsId() == null || reqBean.getProfit() == null || reqBean.getPrice() == null) {
            return ResultGenerator.genFailResult("参数异常！");
        }

        String result = newBeeMallGoodsService.updateNewBeeMallGoods(reqBean);
        if (result.equalsIgnoreCase(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("更新失败");
        }
    }

}