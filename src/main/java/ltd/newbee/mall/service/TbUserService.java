package ltd.newbee.mall.service;

import ltd.newbee.mall.dto.ProfileDto;
import ltd.newbee.mall.dto.RegisterFirstDto;
import ltd.newbee.mall.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 19012964
 * @since 2020-04-14
 */
public interface TbUserService extends IService<TbUser> {
    public Result register(RegisterFirstDto registerFirstDto);

    public Result updateUserProfile(ProfileDto dto);

    public Result getProfile(Long userId);
}
