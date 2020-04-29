package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.common.B2BMallException;
import com.hailian.ylwmall.dao.NewBeeMallGoodsMapper;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.controller.vo.NewBeeMallSearchGoodsVO;
import com.hailian.ylwmall.dao.TbUserDao;
import com.hailian.ylwmall.dto.GoodsAndCompayResDTO;
import com.hailian.ylwmall.dto.GoodsStatusUpdateReqDTO;
import com.hailian.ylwmall.dto.UserListDto;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.util.BeanUtil;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<NewBeeMallGoodsMapper, TbGoodsInfo> implements GoodsService {

    @Autowired
    private NewBeeMallGoodsMapper goodsMapper;
    @Autowired
    private TbUserDao tbUserDao;

    @Override
    public PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil) {
        List<GoodsAndCompayResDTO> goodsList = goodsMapper.findNewBeeMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalNewBeeMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveNewBeeMallGoods(TbGoodsInfo goods) {
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());

        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveNewBeeMallGoods(List<TbGoodsInfo> newBeeMallGoodsList) {
        if (!CollectionUtils.isEmpty(newBeeMallGoodsList)) {
            goodsMapper.batchInsert(newBeeMallGoodsList);
        }
    }

    @Override
    public String updateNewBeeMallGoods(TbGoodsInfo goods) {
        TbGoodsInfo temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (baseMapper.updateById(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public TbGoodsInfo getNewBeeMallGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public Boolean batchUpdateSellStatus(GoodsStatusUpdateReqDTO requstBean, int sellStatus) {
        // 下架要判断是否有未完成的订单（1-待支付 2-待发货 3-待收货 4-待评价 5-售后中）
        if(sellStatus == 6){
            for(Long id : requstBean.getIds()){
                if(!this.checkCanOffline(id)){
                    throw new B2BMallException("有未完成订单不可以下架，请前端下架");
                }
            }
        }
        return goodsMapper.batchUpdateSellStatus(requstBean.getIds(), requstBean.getMsgOffline(), requstBean.getMsgReject(), sellStatus) > 0;
    }

    /**
     * 校验是否可下架
     */
    @Override
    public boolean checkCanOffline(Long googdsId){
        int count = goodsMapper.checkCanOffline(googdsId);
        return count>0 ? false:true;
    }

    @Override
    public PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil) {
        List<TbGoodsInfo> goodsList = goodsMapper.findNewBeeMallGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalNewBeeMallGoodsBySearch(pageUtil);
        List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            newBeeMallSearchGoodsVOS = BeanUtil.copyList(goodsList, NewBeeMallSearchGoodsVO.class);
            for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) {
                String goodsName = newBeeMallSearchGoodsVO.getGoodsName();
                String goodsIntro = newBeeMallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    newBeeMallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(newBeeMallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public List<UserListDto> queryCompanyNameList(){
        UserListDto dto = new UserListDto();
        dto.setUserType("04");
        dto.setUserStatus(1);
        return tbUserDao.queryCompanyNameList(dto);
    }
}
