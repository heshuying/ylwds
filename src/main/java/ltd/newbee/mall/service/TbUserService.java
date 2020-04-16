package ltd.newbee.mall.service;

import ltd.newbee.mall.dto.ProfileDto;
import ltd.newbee.mall.dto.RegisterFirstDto;
import ltd.newbee.mall.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.util.PageUtils;
import ltd.newbee.mall.util.Result;

import java.util.List;
import java.util.Map;

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

    public PageUtils queryUserList(Map<String, Object> params);

    public Integer updateUserStatus(Integer userStatus, List<Long> userIds);
}
