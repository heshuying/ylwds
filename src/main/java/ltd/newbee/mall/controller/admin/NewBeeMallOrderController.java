package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallOrderStatusEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallOrderItemVO;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.entity.IndexConfig;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.entity.order.CommonResult;
import ltd.newbee.mall.entity.order.CutDownPriceParam;
import ltd.newbee.mall.entity.order.DeliverGoodsParam;
import ltd.newbee.mall.service.NewBeeMallIndexConfigService;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.service.NewBeeMallShoppingCartService;
import ltd.newbee.mall.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@Controller
@RequestMapping("/admin")
public class NewBeeMallOrderController {

    @Resource
    private NewBeeMallShoppingCartService newBeeMallShoppingCartService;
    @Resource
    private NewBeeMallOrderService newBeeMallOrderService;

    /**
     * 资源方-订单列表查询功能
     * @param params
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/orders/supplier")
    public String SupplierOrderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        try{
           /* NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
            params.put("userId", user.getUserId());*/
            if (StringUtils.isEmpty(params.get("page"))) {
                params.put("page", 1);
            }
            params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            PageResult result = newBeeMallOrderService.getMyOrdersForSupplier(pageUtil);
            request.setAttribute("pageResult", result);
            return "mall/my-orders";
        } catch (Exception e){
            e.printStackTrace();
            return "error/error_5xx";
        }
    }

    /**
     *  资源方-导出订单
     */
    @RequestMapping("/orders/export")
    @ResponseBody
    public CommonResult exportOrdersForSupplier(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession, HttpServletResponse response){
        try(ServletOutputStream outputStream = response.getOutputStream()){
/*        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());*/
            params.put("page",1);
            params.put("limit",3);
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            pageUtil.put("start",null);
            PageResult result = newBeeMallOrderService.getMyOrdersForSupplier(pageUtil);
            List<?> list = result.getList();
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment;filename=orders.xls");
            newBeeMallOrderService.createExcel(list,outputStream);
            return new CommonResult("200","导出成功");
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult("301","导出失败");
        }
    }


    /**
     * 资源方-发货功能
     * @param params
     * @return
     */
    @RequestMapping(value = "/orders/deliverGoods",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deliverGoods(@RequestBody DeliverGoodsParam params){
        try{
            newBeeMallOrderService.deliverGoods(params);
            return new CommonResult("200","发货成功");
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult("301","发货失败");
        }
    }

    /**
     * 批量发货功能
     * @return
     */
    @RequestMapping(value = "/orders/batchDeliverGoods",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult batchDeliverGoods(@RequestParam MultipartFile file, HttpServletRequest request){
        try{
            newBeeMallOrderService.batchDeliverGoods(file.getInputStream());
            return new CommonResult("200","批量发货成功");
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult("301","批量发货失败");
        }
    }

    /**
     * 平台方-订单列表查询功能
     * @param params
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/orders/platform")
    public String platformOrderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        try{
            if (StringUtils.isEmpty(params.get("page"))) {
                params.put("page", 1);
            }
            params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            PageResult result = newBeeMallOrderService.getMyOrdersForPlatform(pageUtil);
            request.setAttribute("pageResult", result);
            return "mall/my-orders";
        } catch (Exception e){
            e.printStackTrace();
            return "error/error_5xx";
        }
    }


    @PostMapping("/orders/cutDown")
    @ResponseBody
    public CommonResult cutDownPrice(@RequestBody CutDownPriceParam params){
        try{
            newBeeMallOrderService.cutDownPrice(params);
            return new CommonResult("200","减免成功");
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult("301","减免失败");
        }
    }

}