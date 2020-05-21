package com.hailian.ylwmall.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.controller.vo.NewBeeMallUserVO;
import com.hailian.ylwmall.dto.RefundCheckDto;
import com.hailian.ylwmall.dto.RefundStatusDto;
import com.hailian.ylwmall.entity.TbUserAddr;
import com.hailian.ylwmall.entity.order.CommonResult;
import com.hailian.ylwmall.entity.order.CutDownPriceParam;
import com.hailian.ylwmall.entity.order.DeliverGoodsParam;
import com.hailian.ylwmall.service.NewBeeMallOrderService;
import com.hailian.ylwmall.service.TbOrderRefundService;
import com.hailian.ylwmall.service.TbUserAddrService;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.exception.BusinessException;
import com.hailian.ylwmall.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class NewBeeMallOrderController {

    @Resource
    private NewBeeMallOrderService newBeeMallOrderService;
    @Autowired
    private TbUserAddrService userAddrService;
    @Autowired
    private TbOrderRefundService orderRefundService;
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
            String userId = String.valueOf(httpSession.getAttribute("loginUserId"));
            String loginType = (String) httpSession.getAttribute("loginType");
            if(StringUtils.isBlank(userId) || StringUtils.isBlank(loginType) ){
                throw new BusinessException("用户状态异常");
            }
            params.put("supplierId",userId);
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
    public Result cutDownPrice(@RequestBody CutDownPriceParam params, HttpServletRequest request, HttpSession httpSession){
        try{
            String userId = String.valueOf(httpSession.getAttribute("loginUserId"));
            String loginType = (String) httpSession.getAttribute("loginType");
            if(StringUtils.isBlank(userId) || StringUtils.isBlank(loginType) ){
                throw new BusinessException("用户状态异常");
            }
            newBeeMallOrderService.cutDownPrice(params);
            return ResultGenerator.genSuccessResult("减免成功");
        }catch (BusinessException e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult("减免失败");
        }
    }

    /**
     * 编辑我的收获地址
     * @param userAddr
     * @param request
     * @param httpSession
     * @return
     */
    @PostMapping("/delivery/update")
    @ResponseBody
    public Result updateDelivery(@RequestBody TbUserAddr userAddr, HttpServletRequest request, HttpSession httpSession) {
        String userId = String.valueOf(httpSession.getAttribute("loginUserId"));
        userAddrService.updateById(userAddr);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 我的收获地址
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/delivery")
    @ResponseBody
    public Result delivery(HttpServletRequest request, HttpSession httpSession) {
        String userId = String.valueOf(httpSession.getAttribute("loginUserId"));
        List<TbUserAddr> userAddrList=userAddrService.list(
                new QueryWrapper<TbUserAddr>()
                        .eq("user_id",userId)
                        .eq("status",0)
        );
        return ResultGenerator.genSuccessResult(userAddrList);
    }

    /**
     * 新增我的收获地址
     * @param userAddr
     * @param request
     * @param httpSession
     * @return
     */
    @PostMapping("/delivery/add")
    @ResponseBody
    public Result addDelivery(@RequestBody TbUserAddr userAddr, HttpServletRequest request, HttpSession httpSession) {
        String userId = String.valueOf(httpSession.getAttribute("loginUserId"));
        userAddr.setUserId(Long.valueOf(userId));
        userAddr.setCreateTime(new Date());
        userAddr.setStatus(0);
        userAddr.setIsDefault(false);
        userAddrService.save(userAddr);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/refund/check")
    @ResponseBody
    public Result updateRefundStatus(@RequestBody RefundCheckDto dto, HttpSession httpSession) {
        String userId = String.valueOf(httpSession.getAttribute("loginUserId"));
        return orderRefundService.checkRefund(Long.valueOf(userId), dto);
    }

}