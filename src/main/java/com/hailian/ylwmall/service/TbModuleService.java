package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.GoodsQueryDto;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.entity.TbModule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 19012964
 * @since 2020-04-23
 */
public interface TbModuleService extends IService<TbModule> {
    /**
     * 获取banner
     * @return
     */
    Result getBanner();

    Result getBannerAll();

    /**
     * 获取分区
     * @return
     */
    Result getModules();

    List<TbModule> getModuleList();
}
