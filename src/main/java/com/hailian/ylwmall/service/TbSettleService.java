package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.SettleQueryDto;
import com.hailian.ylwmall.dto.SettleUpdateDto;
import com.hailian.ylwmall.entity.TbSettle;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.dto.SettleCreateDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 结算表 服务类
 * </p>
 *
 * @author 19012964
 * @since 2020-04-20
 */
public interface TbSettleService extends IService<TbSettle> {
    /**
     * 查询待结算列表
     * @param queryDto
     * @return
     */
    public Result queryUnSettle(SettleQueryDto queryDto);

    /**
     * 结算列表
     * @param queryDto
     * @return
     */
    public Result querySettle(SettleQueryDto queryDto);

    /**
     * 更新结算状态
     * @param dto
     * @return
     */
    public Result updateSettle(SettleUpdateDto dto);

    /**
     * 发起结算
     * @param dto
     * @return
     */
    public Result createSettle(SettleCreateDto dto);
}
