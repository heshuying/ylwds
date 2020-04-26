package ltd.newbee.mall.dao;

import ltd.newbee.mall.dto.ModuleDetailRes;
import ltd.newbee.mall.entity.TbModuleDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 19012964
 * @since 2020-04-23
 */
public interface TbModuleDetailDao extends BaseMapper<TbModuleDetail> {
    int getModuleDetailCount(Map map);

    List<ModuleDetailRes> getModuleDetailList(Map map);

}
