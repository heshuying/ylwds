package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.dto.BuyFormDto;
import com.hailian.ylwmall.dto.BuyRespDto;
import com.hailian.ylwmall.dto.OrderFormDto;
import com.hailian.ylwmall.dto.OrderSubmitDto;
import com.hailian.ylwmall.dto.ShoppingGoodsDto;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.entity.TbOrderOrderinfo;
import com.hailian.ylwmall.dao.TbOrderOrderinfoDao;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.service.TbOrderOrderinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表（主） 服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-02
 */
@Service
public class TbOrderOrderinfoServiceImpl extends ServiceImpl<TbOrderOrderinfoDao, TbOrderOrderinfo> implements TbOrderOrderinfoService {
    @Autowired
    private GoodsService goodsService;

    @Override
    public Result confirmOrder(OrderFormDto dto) {
        if(dto==null||dto.getGoods()==null){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        List<TbGoodsInfo> goodsInfos=goodsService.list(
                new QueryWrapper<TbGoodsInfo>().in("goods_id",
                        dto.getGoods().stream().map(m->m.getGoodsId()).collect(Collectors.toList()))
        );
        BuyRespDto respDto=new BuyRespDto();
        List<ShoppingGoodsDto> list=new ArrayList<>();
        BigDecimal total=BigDecimal.ZERO;
        BigDecimal expressFee=BigDecimal.ZERO;
        for(BuyFormDto buyFormDto:dto.getGoods()){
            ShoppingGoodsDto shoppingGoodsDto=new ShoppingGoodsDto();
            BeanUtils.copyProperties(buyFormDto, shoppingGoodsDto);
            TbGoodsInfo current=goodsInfos.stream().filter(
                    m->buyFormDto.getGoodsId().compareTo(m.getGoodsId())==0).findAny().orElse(null);
            if(current!=null){
                BeanUtils.copyProperties(current, shoppingGoodsDto);
            }
            list.add(shoppingGoodsDto);
            total.add(shoppingGoodsDto.getPrice().multiply(new BigDecimal(
                    String.valueOf(shoppingGoodsDto.getGoodsCount()))));
            expressFee.add(shoppingGoodsDto.getTransitMoney());
        }
        respDto.setTotal(total.add(expressFee));
        respDto.setExpressFee(expressFee);
        respDto.setList(list);
        return ResultGenerator.genSuccessResult(respDto);
    }

    @Override
    public Result payOrder(String orderNo) {
        return null;
    }

    @Transactional
    @Override
    public Result doOrder(Long userId, OrderSubmitDto dto) {

        TbOrderOrderinfo order=new TbOrderOrderinfo();
        return null;
    }
}
