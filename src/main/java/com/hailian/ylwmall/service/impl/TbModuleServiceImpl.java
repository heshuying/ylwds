package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.dao.TbModuleDao;
import com.hailian.ylwmall.dto.GoodsQueryDto;
import com.hailian.ylwmall.dto.ModuleDetailRes;
import com.hailian.ylwmall.service.TbModuleDetailService;
import com.hailian.ylwmall.service.TbModuleService;
import com.hailian.ylwmall.util.Const;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.dto.ModuleListDto;
import com.hailian.ylwmall.entity.TbModule;
import com.hailian.ylwmall.entity.TbModuleDetail;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.util.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-04-23
 */
@Service
public class TbModuleServiceImpl extends ServiceImpl<TbModuleDao, TbModule> implements TbModuleService {
    @Autowired
    private TbModuleDetailService tbModuleDetailService;

    @Override
    public Result getBanner() {
        TbModule tbModule=baseMapper.selectOne(new QueryWrapper<TbModule>()
            .eq("mod_key", Const.Mod_Banner_Key));

        List<TbModuleDetail> list=tbModuleDetailService.list(
                new QueryWrapper<TbModuleDetail>()
                .eq("mod_id",tbModule.getId())
                .eq("is_deleted", 0)
                .orderByDesc("mod_rank")
                .last("limit 5")
        );
        Result result= ResultGenerator.genSuccessResult();
        result.setData(list);
        return result;
    }

    @Override
    public Result getBannerAll() {
        TbModule tbModule=baseMapper.selectOne(new QueryWrapper<TbModule>()
                .eq("mod_key", Const.Mod_Banner_Key));

        List<TbModuleDetail> list=tbModuleDetailService.list(
                new QueryWrapper<TbModuleDetail>()
                        .eq("mod_id",tbModule.getId())
                        .eq("is_deleted", 0)
                        .orderByDesc("mod_rank")
        );
        Result result= ResultGenerator.genSuccessResult();
        result.setData(list);
        return result;
    }

    @Override
    public Result getModules() {
        List<TbModule> tbModules=baseMapper.selectList(new QueryWrapper<TbModule>()
                .eq("mod_key", Const.Mod_Module_Key)
                .eq("is_deleted", 0)
                .orderByDesc("mod_rank")
        );
        List<Long> ids=tbModules.stream().map(m->m.getId()).collect(Collectors.toList());

        List<ModuleListDto> list=new ArrayList<>();
        for (TbModule module: tbModules
             ) {
            ModuleListDto dto=new ModuleListDto();
            BeanUtils.copyProperties(module, dto);
            dto.setModId(module.getId());

            List<ModuleDetailRes> currDetails= tbModuleDetailService.getModeleDetails(module.getId(), 5);
            //专区产品
            List<ModuleDetailRes> nonHead=currDetails.stream().filter(m->m.getIsHead().equals("0"))
                    .limit(4).collect(Collectors.toList());

            dto.setList(nonHead);
            //首图
            ModuleDetailRes head=currDetails.stream().filter(
                    m->m.getIsHead().equals("1")
            ).findAny().orElse(null);
            dto.setHeadProd(head);
            list.add(dto);
        }
        Result result= ResultGenerator.genSuccessResult();
        result.setData(list);
        return result;
    }

    /**
     * 有效的专区列表
     * @return
     */
    @Override
    public List<TbModule> getModuleList(){
        List<TbModule> tbModules=baseMapper.selectList(new QueryWrapper<TbModule>()
                .eq("mod_key", Const.Mod_Module_Key)
                .eq("is_deleted", 0)
                .orderByDesc("mod_rank")
        );
        return tbModules;
    }

    public void getModuleDetails(){

    }


}
