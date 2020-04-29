package com.hailian.ylwmall.service;

import com.hailian.ylwmall.util.PageResult;
import com.hailian.ylwmall.entity.TbModuleDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hailian.ylwmall.util.PageQueryUtil;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 19012964
 * @since 2020-04-23
 */
public interface TbModuleDetailService extends IService<TbModuleDetail> {

    PageResult getModuleDetails(PageQueryUtil pageUtil);
}
