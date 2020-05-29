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

import java.util.ArrayList;
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
    public List<ModuleDetailRes> getModeleDetails(Long modId, Integer limitNum) {
        return moduleDetailDao.getModultProduct(modId, limitNum);
    }

    @Override
    public Result getSimpleGoods(GoodsQueryDto dto) {
        if(dto==null|| dto.getOrderCond()==0){
            dto.setOrderby(" goods_id desc ");
        }else if(dto.getOrderCond()==1){
            if(dto.isDesc()) {
                dto.setOrderby(" price desc ");
            }else{
                dto.setOrderby(" price asc ");
            }
        }else{
            if(dto.isDesc()) {
                dto.setOrderby(" sale_total  desc ");
            }else{
                dto.setOrderby(" sale_total  asc ");
            }
        }
        List<GoodsSimpleDto> list=new ArrayList<>();
        if(dto.getModuleId()>0){
            list= baseMapper.getProductByModule(dto);
        }else{
            list=baseMapper.getSimpleGoods(dto);
        }
        return ResultGenerator.genSuccessResult(list);
    }
}
