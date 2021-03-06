package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.dto.ProfileDto;
import com.hailian.ylwmall.dto.RegisterFirstDto;
import com.hailian.ylwmall.dto.UserListDto;
import com.hailian.ylwmall.entity.TbUser;
import com.hailian.ylwmall.entity.TbUserAddr;
import com.hailian.ylwmall.entity.TbUserProfile;
import com.hailian.ylwmall.service.TbUserAddrService;
import com.hailian.ylwmall.service.TbUserProfileService;
import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.Const;
import com.hailian.ylwmall.util.PageUtils;
import com.hailian.ylwmall.util.Query;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.dao.TbUserDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.util.MD5Util;
import com.hailian.ylwmall.util.ResultGenerator;
import com.hailian.ylwmall.wsdl.buyer.CreatePlCust2MDM_CreatePlCust2MDMPt_Client;
import com.hailian.ylwmall.wsdl.supplier.OuterSysVendorToMDMNEW_OuterSysVendorToMDMNEWPt_Client;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        //更新状态
        TbUser user = baseMapper.selectById(dto.getUserId());
        user.setUserId(dto.getUserId());
        user.setUserStatus(Const.UserStatus.Uncheck.getKey());
        if(StringUtils.isNoneBlank(dto.getRegCellphone())){
            user.setCellphone(dto.getRegCellphone());
        }
        baseMapper.updateById(user);

        TbUserAddr existUserAddr=userAddrService.getOne(
                new QueryWrapper<TbUserAddr>().eq("user_id",dto.getUserId()).eq("status",0)
        );
        TbUserAddr tbUserAddr=new TbUserAddr();
        tbUserAddr.setProvince(dto.getDeliveryProv());
        tbUserAddr.setCity(dto.getDeliveryCity());
        tbUserAddr.setArea(dto.getDeliveryArea());
        tbUserAddr.setDetail(dto.getDeliveryDetail());
        tbUserAddr.setPhone(dto.getDeliveryPhone());
        tbUserAddr.setUserId(dto.getUserId());
        tbUserAddr.setStatus(0);
        tbUserAddr.setIsDefault(true);
        tbUserAddr.setAcceptor(dto.getAcceptor());
        if(existUserAddr==null){
            userAddrService.save(tbUserAddr);
        }else{
            tbUserAddr.setAddrId(existUserAddr.getAddrId());
            userAddrService.updateById(tbUserAddr);
        }

        // 调用mdm
        /*if("02".equals(user.getUserType())){
            // 采购调用mdm
            String mdmCode = CreatePlCust2MDM_CreatePlCust2MDMPt_Client.callMdm(dto);
            user.setMdmCode(mdmCode);
            baseMapper.updateById(user);
        }else if("04".equals(user.getUserType())){
            Map<String, String> map = OuterSysVendorToMDMNEW_OuterSysVendorToMDMNEWPt_Client.callMdm(dto);
            user.setMdmCode(map.get("outVENDORCODE"));
            user.setOutTaxCode(map.get("out_tax_code"));
            baseMapper.updateById(user);
        }*/

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
        TbUser user=baseMapper.selectById(userId);
        profileDto.setRegCellphone(user.getCellphone());
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

    @Override
    public PageUtils queryUserList(Map<String, Object> params) {
        UserListDto queryDto =new UserListDto();
        if(params.containsKey("loginName")){
            queryDto.setLoginName((String) params.get("loginName"));
        }
        if(params.containsKey("userType")){
            queryDto.setUserType((String) params.get("userType"));
        }
        if(params.containsKey("userStatus")){
            queryDto.setUserStatus(Integer.valueOf((String)params.get("userStatus")));
        }

        IPage<UserListDto> result= baseMapper.queryUser(
                new Query<UserListDto>().getPage(params),
                queryDto);

        return new PageUtils(result);
    }

    @Override
    public Integer updateUserStatus(Integer userStatus, List<Long> userIds) {
        return baseMapper.updateUserStatus(userStatus, userIds);
    }
}
