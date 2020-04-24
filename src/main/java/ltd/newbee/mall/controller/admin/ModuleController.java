package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.dto.ModuleDetailAddRes;
import ltd.newbee.mall.dto.ModuleDetailReq;
import ltd.newbee.mall.dto.ModuleReq;
import ltd.newbee.mall.entity.TbGoodsInfo;
import ltd.newbee.mall.entity.TbModule;
import ltd.newbee.mall.entity.TbModuleDetail;
import ltd.newbee.mall.service.GoodsService;
import ltd.newbee.mall.service.TbModuleDetailService;
import ltd.newbee.mall.service.TbModuleService;
import ltd.newbee.mall.util.CommonUtil;
import ltd.newbee.mall.util.Const;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

/**
 * 运营专区
 * @author 19033323
 */
@RestController
@RequestMapping("/admin/module")
public class ModuleController {
    @Autowired
    private TbModuleService moduleService;
    @Autowired
    private TbModuleDetailService moduleDetailService;
    @Autowired
    private GoodsService goodsService;

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

    /**
     * 专区内容新增修改
     * @param reqBean
     * @return
     */
    @PostMapping("/saveModelDetail")
    public Result saveModelDetail(@RequestBody ModuleDetailReq reqBean){
        if(reqBean == null || StringUtils.isBlank(reqBean.getIsHead())){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if("1".equals(reqBean.getIsHead()) && StringUtils.isBlank(reqBean.getJumpUrl())){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if("0".equals(reqBean.getIsHead()) && Objects.isNull(reqBean.getGoodsId())){
            return ResultGenerator.genFailResult("参数异常！");
        }

        TbModuleDetail module = CommonUtil.convertBean(reqBean, TbModuleDetail.class);
        module.setUpdateTime(new Date());
        if(moduleDetailService.saveOrUpdate(module)){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("保存失败");
    }

    /**
     * 专区内容详情
     */
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

    @GetMapping("getModuleDetails")
    public Result getModuleDetails(){
        return ResultGenerator.genSuccessResult(moduleService.getModuleList());
    }

}
