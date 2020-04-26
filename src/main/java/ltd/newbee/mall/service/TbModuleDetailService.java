package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.TbModuleDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

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
