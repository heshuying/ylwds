package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ltd.newbee.mall.dto.UserListDto;
import ltd.newbee.mall.entity.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 19012964
 * @since 2020-04-14
 */
public interface TbUserDao extends BaseMapper<TbUser> {
    IPage<UserListDto> queryUser(@Param("pg") IPage<?> page, @Param("dto") UserListDto dto);

    Integer updateUserStatus(@Param("userStatus")Integer userStatus, @Param("userIds") List<Long> userIds);

    List<UserListDto> queryCompanyNameList(@Param("dto") UserListDto dto);
}
