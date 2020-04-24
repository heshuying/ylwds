package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.TbModule;
import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.util.Result;

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

    /**
     * 获取分区
     * @return
     */
    Result getModules();

    List<TbModule> getModuleList();
}
