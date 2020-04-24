package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.dto.ModuleReq;
import ltd.newbee.mall.entity.TbModule;
import ltd.newbee.mall.service.TbModuleService;
import ltd.newbee.mall.util.CommonUtil;
import ltd.newbee.mall.util.Const;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 运营专区
 * @author 19033323
 */
@RestController
@RequestMapping("/admin/module")
public class ModuleController {
    @Autowired
    private TbModuleService moduleService;

    /**
     * 专区新增及修改
     * @param reqBean
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody ModuleReq reqBean){
        if(reqBean == null || StringUtils.isBlank(reqBean.getModName())){
            return ResultGenerator.genFailResult("参数异常！");
        }
        TbModule module = CommonUtil.convertBean(reqBean, TbModule.class);
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
    @GetMapping("getModuleInfo/{moduleId}")
    public Result getModuleInfo(@PathVariable("moduleId") Long moduleId){
        TbModule module = moduleService.getById(moduleId);
        return ResultGenerator.genSuccessResult(module);
    }

    /**
     * 专区列表
     * @return
     */
    @GetMapping("getModules")
    public Result getModules(){
        return ResultGenerator.genSuccessResult(moduleService.getModuleList());
    }

}
