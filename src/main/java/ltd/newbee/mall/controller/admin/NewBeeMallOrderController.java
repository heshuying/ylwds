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
import ltd.newbee.mall.exception.BusinessException;
import ltd.newbee.mall.service.NewBeeMallIndexConfigService;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.service.NewBeeMallShoppingCartService;
import ltd.newbee.mall.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class NewBeeMallOrderController {

    @Resource
    private NewBeeMallOrderService newBeeMallOrderService;

    /**
     *
     * @param params
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/orders/page")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        try{
            String loginUserId = (String) httpSession.getAttribute("loginUserId");
            String loginType = (String) httpSession.getAttribute("loginType");
            if(StringUtils.isBlank(loginUserId)){
                return "error/error_5xx";
            }
            if("03".equals(loginType)){
                request.setAttribute("path", "newbee_mall_supplier_order");
                return "admin/busiOrderManage";
            }else if("04".equals(loginType)){
                request.setAttribute("path", "newbee_mall_platform_order");
                return "admin/platOrderManage";
            }else {
                return "error/error_5xx";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "error/error_5xx";
        }
    }

    /**
     * 资源方-订单列表页面
     * @param params
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/orders/supplier/page")
    public String supplierOrderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        try{
            request.setAttribute("path", "newbee_mall_supplier_order");
            return "admin/busiOrderManage";
        } catch (Exception e){
            e.printStackTrace();
            return "error/error_5xx";
        }
    }

    /**
     * 平台方-订单列表页面
     * @param params
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/orders/platform/page")
    public String platformOrderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        try{
            request.setAttribute("path", "newbee_mall_platform_order");
            return "admin/platOrderManage";
        } catch (Exception e){
            e.printStackTrace();
            return "error/error_5xx";
        }
    }

    /**
     * 资源方-订单列表查询功能
     * @param params
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/orders/supplier")
    @ResponseBody
    public Result supplierOrderList(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        try{
            String userId = String.valueOf(httpSession.getAttribute("loginUserId"));
            String loginType = (String) httpSession.getAttribute("loginType");
            if(StringUtils.isBlank(userId) || StringUtils.isBlank(loginType) ){
                throw new BusinessException("用户状态异常");
            }
            params.put("supplierId",userId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isEmpty((String)params.get("page"))) {
                params.put("page", 1);
            }
            Object beginTime = params.get("beginTime");
            if(beginTime != null && !((String)beginTime).equals("")){
                params.put("beginTime",sdf.parse((String)beginTime));
            }else {
                params.remove("beginTime");
            }

            Object endTime = params.get("endTime");
            if(endTime != null && !((String)endTime).equals("")){
                params.put("endTime",sdf.parse((String)endTime));
            }else {
                params.remove("endTime");
            }
            if(params.get("limit") == null || (params.get("limit")).equals("")){
                params.put("limit","10");
            }
            Object descOrAsc = params.get("descOrAsc");
            if(descOrAsc == null || ((String)descOrAsc).equals("")){
                params.put("descOrAsc","desc");
            }
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            PageResult result = newBeeMallOrderService.getMyOrdersForSupplier(pageUtil);
            return ResultGenerator.genSuccessResult(result);
        } catch (BusinessException e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult("资源方订单列表查询失败");
        }
    }

    /**
     *  资源方-导出订单
     */
    @RequestMapping(value = "/orders/export")
    @ResponseBody
    public CommonResult exportOrdersForSupplier(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession, HttpServletResponse response){
        try(ServletOutputStream outputStream = response.getOutputStream()){
/*        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());*/
            params.put("page",1);
            params.put("limit",3);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object beginTime = params.get("beginTime");
            if(beginTime != null && !((String)beginTime).equals("")){
                params.put("beginTime",sdf.parse((String)beginTime));
            }else {
                params.remove("beginTime");
            }

            Object endTime = params.get("endTime");
            if(endTime != null && !((String)endTime).equals("")){
                params.put("endTime",sdf.parse((String)endTime));
            }else {
                params.remove("endTime");
            }

            Object descOrAsc = params.get("descOrAsc");
            if(descOrAsc == null || ((String)descOrAsc).equals("")){
                params.put("descOrAsc","desc");
            }
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            pageUtil.put("start",null);
            PageResult result = newBeeMallOrderService.getMyOrdersForSupplier(pageUtil);
            List<?> list = result.getList();
            //response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment;filename=orders.xls");
            response.setCharacterEncoding("utf-8");
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
    public CommonResult batchDeliverGoods(@RequestParam MultipartFile file,  HttpServletRequest request){
        try{
            newBeeMallOrderService.batchDeliverGoods(file.getInputStream());
            return new CommonResult("200","批量发货成功");
        }catch (BusinessException e){
            e.printStackTrace();
            return new CommonResult("302",e.getMessage());
        } catch (Exception e){
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
    @ResponseBody
    public Result platformOrderList(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        try{
            String userId = String.valueOf(httpSession.getAttribute("loginUserId"));
            String loginType = (String) httpSession.getAttribute("loginType");
            if(StringUtils.isBlank(userId) || StringUtils.isBlank(loginType) ){
                throw new BusinessException("用户状态异常");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isEmpty((String)params.get("page"))) {
                params.put("page", 1);
            }
            Object beginTime = params.get("beginTime");
            if(beginTime != null && !((String)beginTime).equals("")){
                params.put("beginTime",sdf.parse((String)beginTime));
            }else {
                params.remove("beginTime");
            }

            Object endTime = params.get("endTime");
            if(endTime != null && !((String)endTime).equals("")){
                params.put("endTime",sdf.parse((String)endTime));
            }else {
                params.remove("endTime");
            }
            if(params.get("limit") == null || (params.get("limit")).equals("")){
                params.put("limit","10");
            }

            Object descOrAsc = params.get("descOrAsc");
            if(descOrAsc == null || ((String)descOrAsc).equals("")){
                params.put("descOrAsc","desc");
            }
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            PageResult result = newBeeMallOrderService.getMyOrdersForPlatform(pageUtil);
            return ResultGenerator.genSuccessResult(result);
        } catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult("平台方订单列表查询失败");
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