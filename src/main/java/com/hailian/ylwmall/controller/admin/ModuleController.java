package com.hailian.ylwmall.controller.admin;

import com.hailian.ylwmall.dto.ModuleDetailReq;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.util.CommonUtil;
import com.hailian.ylwmall.util.Const;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.dto.ModuleDetailAddRes;
import com.hailian.ylwmall.dto.ModuleReq;
import com.hailian.ylwmall.entity.TbModule;
import com.hailian.ylwmall.entity.TbModuleDetail;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.service.TbModuleDetailService;
import com.hailian.ylwmall.service.TbModuleService;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 运营专区
 * @author 19033323
 */
@Controller
@RequestMapping("/admin/module")
public class ModuleController {
    @Autowired
    private TbModuleService moduleService;
    @Autowired
    private TbModuleDetailService moduleDetailService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 轮播页面
     * @param request
     * @return
     */
    @GetMapping("/platRunBanner")
    public String platRunBanner(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_run_banner");
        return "admin/platRunBanner";
    }

    /**
     * 专区页面
     * @param request
     * @return
     */
    @GetMapping("/platRunSpeArea")
    public String platRunSpeArea(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_run_banner");
        return "admin/platRunSpeArea";
    }

    /**
     * 专区新增及修改
     * @param reqBean
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(@RequestBody ModuleReq reqBean, HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("loginUserId");
        if(Objects.isNull(userId)){
            return ResultGenerator.genFailResult("未登录！");
        }
        if(reqBean == null || StringUtils.isEmpty(reqBean.getModName())){
            return ResultGenerator.genFailResult("参数异常！");
        }

        TbModule module = CommonUtil.convertBean(reqBean, TbModule.class);
        module.setCreateUser(userId.intValue());
        module.setUpdateUser(userId.intValue());
        module.setUpdateTime(new Date());
        module.setModKey(Const.Mod_Module_Key);
        if(moduleService.saveOrUpdate(module)){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("专区保存失败");
    }

    /**
     * 专区详情
     * @param moduleId
     * @return
     */
    @ResponseBody
    @GetMapping("getModuleInfo/{moduleId}")
    public Result getModuleInfo(@PathVariable("moduleId") Long moduleId){
        TbModule module = moduleService.getById(moduleId);
        return ResultGenerator.genSuccessResult(module);
    }

    /**
     * 专区列表
     * @return
     */
    @ResponseBody
    @GetMapping("getModules")
    public Result getModules(){
        return ResultGenerator.genSuccessResult(moduleService.getModuleList());
    }

    /**
     * 专区内容新增修改
     * @param reqBean
     * @return
     */
    @ResponseBody
    @PostMapping("/saveModelDetail")
    public Result saveModelDetail(@RequestBody ModuleDetailReq reqBean, HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("loginUserId");
        if(Objects.isNull(userId)){
            return ResultGenerator.genFailResult("未登录！");
        }
        if(reqBean == null
                || StringUtils.isEmpty(reqBean.getIsHead())
                || StringUtils.isEmpty(reqBean.getModId())){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if("1".equals(reqBean.getIsHead()) && StringUtils.isEmpty(reqBean.getJumpUrl())){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if("0".equals(reqBean.getIsHead()) && Objects.isNull(reqBean.getGoodsId())){
            return ResultGenerator.genFailResult("参数异常！");
        }

        TbModuleDetail module = CommonUtil.convertBean(reqBean, TbModuleDetail.class);
        module.setCreateUser(userId.intValue());
        module.setUpdateUser(userId.intValue());
        module.setUpdateTime(new Date());
        if(moduleDetailService.saveOrUpdate(module)){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("保存失败");
    }

    /**
     * 专区内容详情
     */
    @ResponseBody
    @GetMapping("getModuleDetailInfo/{id}")
    public Result getModuleDetailInfo(@PathVariable("id") Long id){
        ModuleDetailAddRes res = new ModuleDetailAddRes();
        TbModuleDetail module = moduleDetailService.getById(id);
        if(module == null){
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        BeanUtils.copyProperties(module, res);

        if(module.getGoodsId() != null && module.getGoodsId() != 0){
            // 查询对应产品名称
            TbGoodsInfo goodsInfo = goodsService.getNewBeeMallGoodsById(module.getGoodsId());
            if(goodsInfo == null){
                return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
            }
            res.setGoodsName(goodsInfo.getGoodsName());
        }
        return ResultGenerator.genSuccessResult(res);
    }

    /**
     * 专区详情列表
     * @return
     */
    @ResponseBody
    @GetMapping("getModuleDetails")
    public Result getModuleDetails(@RequestParam Map<String, Object> params){
        if (org.springframework.util.StringUtils.isEmpty(params.get("page"))
                || StringUtils.isEmpty(params.get("limit"))
                || StringUtils.isEmpty(params.get("modId"))){
            return ResultGenerator.genFailResult("参数异常！");
        }

        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(moduleDetailService.getModuleDetails(pageUtil));
    }

}
