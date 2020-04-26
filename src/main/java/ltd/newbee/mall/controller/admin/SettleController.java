package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallOrderItemVO;
import ltd.newbee.mall.dto.SettleCreateDto;
import ltd.newbee.mall.dto.SettleQueryDto;
import ltd.newbee.mall.dto.SettleUpdateDto;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.service.TbSettleService;
import ltd.newbee.mall.util.DateTimeUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/settle")
public class SettleController {

    @Autowired
    private TbSettleService tbSettleService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/settle")
    public String settlePage(HttpServletRequest request) {
        request.setAttribute("path", "settle");
        return "admin/newbee_mall_order";
    }

    /**
     * 平台待结算列表
     */
    @RequestMapping(value = "/settle/plateform/list", method = RequestMethod.GET)
    @ResponseBody
    public Result settleList(@RequestParam Map<String, Object> params) {
        SettleQueryDto queryDto=new SettleQueryDto();
        queryDto.setSupplierId((Long) params.get("userId"));
        queryDto.setStartDate(DateTimeUtil.stringToDate((String) params.get("startDate"),
                DateTimeUtil.DATE_PATTERN) );
        queryDto.setEndDate(DateTimeUtil.stringToDate((String) params.get("endDate"),
                DateTimeUtil.DATE_PATTERN));

        return tbSettleService.queryUnSettle(queryDto);
    }

    /**
     * 平台已发起列表
     */
    @RequestMapping(value = "/settled/plateform/list", method = RequestMethod.GET)
    @ResponseBody
    public Result settledList(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        SettleQueryDto queryDto=new SettleQueryDto();
        if(params.containsKey("userId")){
            queryDto.setSupplierId((Long) params.get("userId"));
        }
        if(params.containsKey("status")){
            queryDto.setStatus((Integer) params.get("status"));
        }
        queryDto.setPage((Integer)params.get("page"));
        queryDto.setLimit((Integer)params.get("limit"));
        return tbSettleService.querySettle(queryDto);
    }

    /**
     * 供应商已发起列表
     */
    @RequestMapping(value = "/settle/supplier/list", method = RequestMethod.GET)
    @ResponseBody
    public Result supplierList(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        SettleQueryDto queryDto=new SettleQueryDto();
        queryDto.setSupplierId(Long.parseLong(loginUserId.toString()));

        if(params.containsKey("status")){
            queryDto.setStatus((Integer) params.get("status"));
        }
        queryDto.setPage((Integer)params.get("page"));
        queryDto.setLimit((Integer)params.get("limit"));
        return tbSettleService.querySettle(queryDto);
    }


    /**
     * 更新结算状态
     */
    @RequestMapping(value = "/settle/update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateSettleStatus(@RequestBody SettleUpdateDto dto) {
        if (dto==null||dto.getStatus()==null||dto.getSettleId()==null) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        return tbSettleService.updateSettle(dto);
    }

    /**
     * 更新结算状态
     */
    @RequestMapping(value = "/settle/create", method = RequestMethod.POST)
    @ResponseBody
    public Result createSettle(@RequestBody SettleCreateDto dto) {
        if (dto==null||dto.getSupplierId()==null||dto.getUnSettleList()==null) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        return tbSettleService.createSettle(dto);
    }
}