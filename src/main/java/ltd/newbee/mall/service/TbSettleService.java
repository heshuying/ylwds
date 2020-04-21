package ltd.newbee.mall.service;

import ltd.newbee.mall.dto.SettleCreateDto;
import ltd.newbee.mall.dto.SettleQueryDto;
import ltd.newbee.mall.dto.SettleUpdateDto;
import ltd.newbee.mall.entity.TbSettle;
import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.util.Result;

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
