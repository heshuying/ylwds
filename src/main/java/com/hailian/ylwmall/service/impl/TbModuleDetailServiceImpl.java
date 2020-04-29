package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.dao.TbModuleDetailDao;
import com.hailian.ylwmall.service.TbModuleDetailService;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;
import com.hailian.ylwmall.dto.ModuleDetailRes;
import com.hailian.ylwmall.entity.TbModuleDetail;
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

    @Autowired TbModuleDetailDao moduleDetailDao;

    @Override
    public PageResult getModuleDetails(PageQueryUtil pageUtil){
        List<ModuleDetailRes> list = moduleDetailDao.getModuleDetailList(pageUtil);
        int count = moduleDetailDao.getModuleDetailCount(pageUtil);
        PageResult pageResult = new PageResult(list, count, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
