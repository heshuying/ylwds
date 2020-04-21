package ltd.newbee.mall.dao;

import ltd.newbee.mall.dto.UnSettleListDto;
import ltd.newbee.mall.entity.TbSettle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 结算表 Mapper 接口
 * </p>
 *
 * @author 19012964
 * @since 2020-04-20
 */
public interface TbSettleDao extends BaseMapper<TbSettle> {
    List<UnSettleListDto> querySettleList(@Param("supplierId") Long supplierId,
                                          @Param("startDate") Date startDate,
                                          @Param("endDate") Date endDate);
}
