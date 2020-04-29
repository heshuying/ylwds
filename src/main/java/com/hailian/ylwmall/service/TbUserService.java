package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.ProfileDto;
import com.hailian.ylwmall.dto.RegisterFirstDto;
import com.hailian.ylwmall.util.PageUtils;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

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
