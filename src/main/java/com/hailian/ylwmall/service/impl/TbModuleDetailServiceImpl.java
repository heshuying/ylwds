package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.dao.TbModuleDetailDao;
import com.hailian.ylwmall.dto.GoodsQueryDto;
import com.hailian.ylwmall.dto.GoodsSimpleDto;
import com.hailian.ylwmall.service.TbModuleDetailService;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;
import com.hailian.ylwmall.dto.ModuleDetailRes;
import com.hailian.ylwmall.entity.TbModuleDetail;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-04-23
 */
@Service
public class TbModuleDetailServiceImpl extends ServiceImpl<TbModuleDetailDao, TbModuleDetail> implements TbModuleDetailService {

    @Autowired
    private TbModuleDetailDao moduleDetailDao;

    @Override
    public PageResult getModuleDetails(PageQueryUtil pageUtil){
        List<ModuleDetailRes> list = moduleDetailDao.getModuleDetailList(pageUtil);
        int count = moduleDetailDao.getModuleDetailCount(pageUtil);
        PageResult pageResult = new PageResult(list, count, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public List<ModuleDetailRes> getModeleDetails() {
        return moduleDetailDao.getAllModule();
    }

    @Override
    public Result getSimpleGoods(GoodsQueryDto dto) {
        if(dto==null|| StringUtils.isBlank(dto.getOrderby())){
            dto.setOrderby(" goods_id desc ");
        }
        List<GoodsSimpleDto> list=baseMapper.getSimpleGoods(dto);
        return ResultGenerator.genSuccessResult(list);
    }
}
