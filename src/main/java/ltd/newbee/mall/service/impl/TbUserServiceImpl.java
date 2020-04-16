package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.dto.ProfileDto;
import ltd.newbee.mall.dto.RegisterFirstDto;
import ltd.newbee.mall.entity.TbUser;
import ltd.newbee.mall.dao.TbUserDao;
import ltd.newbee.mall.entity.TbUserAddr;
import ltd.newbee.mall.entity.TbUserProfile;
import ltd.newbee.mall.service.TbUserAddrService;
import ltd.newbee.mall.service.TbUserProfileService;
import ltd.newbee.mall.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.util.Const;
import ltd.newbee.mall.util.MD5Util;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin.util.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-04-14
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserDao, TbUser> implements TbUserService {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private TbUserProfileService profileService;
    @Autowired
    private TbUserAddrService userAddrService;
    @Override
    public Result register(RegisterFirstDto registerFirstDto) {
        if(registerFirstDto==null|| StringUtils.isBlank(registerFirstDto.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        if(!registerFirstDto.getConfirmPwd().equals(registerFirstDto.getPassword())){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_PWD.getResult());
        }
        //校验验证码
        String kaptchaCode = "";
        if(Const.UserType.Cus_Company.getKey().equals(registerFirstDto.getUserType())){
            kaptchaCode=httpServletRequest.getSession().getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        }else{
            kaptchaCode= httpServletRequest.getSession().getAttribute(Constants.Manage_Verify_Code) + "";
        }

        if (StringUtils.isBlank(registerFirstDto.getValidCode())
                || !registerFirstDto.getValidCode().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        //校验用户名是否存在
        TbUser user=baseMapper.selectOne(new QueryWrapper<TbUser>()
            .eq("user_type",registerFirstDto.getUserType())
        .eq("login_name",registerFirstDto.getLoginName())
        .last("limit 1"));
        if(user!=null){
            return ResultGenerator.genFailResult("用户名"+ServiceResultEnum.FAIL_EXISTS.getResult());
        }
        user=new TbUser();
        user.setLoginName(registerFirstDto.getLoginName());
        user.setNickName(registerFirstDto.getLoginName());
        String passwordMD5 = MD5Util.MD5Encode(registerFirstDto.getConfirmPwd(), "UTF-8");
        user.setPasswordMd5(passwordMD5);
        user.setUserType(registerFirstDto.getUserType());
        user.setUserStatus(Const.UserStatus.Uncheck.getKey());
        user.setCreateTime(new Date());
        baseMapper.insert(user);

        TbUserProfile profile=new TbUserProfile();
        profile.setUserId(user.getUserId());
        profile.setCompanyName(user.getLoginName());
        profile.setCreateTime(new Date());
        profileService.save(profile);

        Result<Long> result= ResultGenerator.genSuccessResult();
        result.setData(user.getUserId());
        return result;
    }

    @Override
    public Result updateUserProfile(ProfileDto dto) {
        if(dto==null||dto.getUserId()==null){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        TbUserProfile tbUserProfile=new TbUserProfile();
        BeanUtils.copyProperties(dto, tbUserProfile);
        profileService.update(tbUserProfile, new QueryWrapper<TbUserProfile>()
                .eq("user_id",dto.getUserId()));
        if(StringUtils.isNoneBlank(dto.getRegCellphone())){
            TbUser user=new TbUser();
            user.setUserId(dto.getUserId());
            user.setCellphone(dto.getRegCellphone());
            baseMapper.updateById(user);
        }

        TbUserAddr existUserAddr=userAddrService.getOne(
                new QueryWrapper<TbUserAddr>().eq("user_id",dto.getUserId())
        );
        TbUserAddr tbUserAddr=new TbUserAddr();
        tbUserAddr.setProvince(dto.getDeliveryProv());
        tbUserAddr.setCity(dto.getDeliveryCity());
        tbUserAddr.setArea(dto.getDeliveryArea());
        tbUserAddr.setDetail(dto.getDeliveryDetail());
        tbUserAddr.setPhone(dto.getDeliveryPhone());
        tbUserAddr.setUserId(dto.getUserId());
        tbUserAddr.setAcceptor(dto.getAcceptor());
        if(existUserAddr==null){
            userAddrService.save(tbUserAddr);
        }else{
            tbUserAddr.setAddrId(existUserAddr.getAddrId());
            userAddrService.updateById(tbUserAddr);
        }


        return ResultGenerator.genSuccessResult();
    }

    @Override
    public Result getProfile(Long userId) {
        if(userId==null){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        TbUserProfile tbUserProfile=profileService.getOne(
                new QueryWrapper<TbUserProfile>().eq("user_id",userId)
        );
        TbUserAddr tbUserAddr=userAddrService.getOne(
                new QueryWrapper<TbUserAddr>().eq("user_id",userId)
        );
        ProfileDto profileDto=new ProfileDto();
        BeanUtils.copyProperties(tbUserProfile, profileDto);
        if(tbUserAddr!=null){
            profileDto.setDeliveryProv(tbUserAddr.getProvince());
            profileDto.setDeliveryCity(tbUserAddr.getCity());
            profileDto.setDeliveryArea(tbUserAddr.getArea());
            profileDto.setDeliveryDetail(tbUserAddr.getDetail());
            profileDto.setDeliveryPhone(tbUserAddr.getPhone());
            profileDto.setAcceptor(tbUserAddr.getAcceptor());
        }
        Result<ProfileDto> result= ResultGenerator.genSuccessResult();
        result.setData(profileDto);
        return result;
    }
}
