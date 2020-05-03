package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.OrderStatusEnum;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.dto.BuyFormDto;
import com.hailian.ylwmall.dto.BuyRespDto;
import com.hailian.ylwmall.dto.OrderFormDto;
import com.hailian.ylwmall.dto.OrderSubmitDto;
import com.hailian.ylwmall.dto.ShoppingGoodsDto;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.entity.TbOrderGoodinfo;
import com.hailian.ylwmall.entity.TbOrderOrderinfo;
import com.hailian.ylwmall.dao.TbOrderOrderinfoDao;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.service.TbOrderGoodinfoService;
import com.hailian.ylwmall.service.TbOrderOrderinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import com.hailian.ylwmall.util.SystemUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private TbOrderGoodinfoService orderGoodsService;
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
            expressFee.add(shoppingGoodsDto.getTransitMoney().multiply(new BigDecimal(
                    String.valueOf(shoppingGoodsDto.getGoodsCount()))));
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
        //根据供应商分开产生订单
        BuyRespDto buyRespDto=dto.getBuyGoods();
        List<ShoppingGoodsDto> orderGoods=buyRespDto.getList();
        List<Long> supplierIds=orderGoods.stream().map(m->m.getSupplierId()).distinct().collect(Collectors.toList());
        List<TbOrderOrderinfo> orders=new ArrayList<>();
        List<TbOrderGoodinfo> orderGoodinfos=new ArrayList<>();
        List<Long> orderIds=new ArrayList<>();
        for (Long supplier : supplierIds){
            List<ShoppingGoodsDto> currentSupplierGoods=orderGoods.stream().filter(
                    m->supplier.compareTo(m.getSupplierId())==0
            ).collect(Collectors.toList());
            TbOrderOrderinfo order=new TbOrderOrderinfo();
            Long orderId= SystemUtil.buildOrderNo(supplier);
            order.setId( orderId);
            order.setCreateTime(new Date());
            order.setCustomerId(userId);
            order.setSupplierId(supplier);
            order.setDeliveryId(dto.getDeliveryId());
            order.setStatus(OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus());

            //供应商总价
            BigDecimal supplierFee=BigDecimal.ZERO;
            BigDecimal expressFee=BigDecimal.ZERO;
            BigDecimal originFee=BigDecimal.ZERO;
            BigDecimal plateFee=BigDecimal.ZERO;
            for(ShoppingGoodsDto shoppingGoodsDto:currentSupplierGoods){
                supplierFee.add(shoppingGoodsDto.getSellingPrice().multiply(new BigDecimal(
                        String.valueOf(shoppingGoodsDto.getGoodsCount()))));
                expressFee.add(shoppingGoodsDto.getTransitMoney().multiply(new BigDecimal(
                        String.valueOf(shoppingGoodsDto.getGoodsCount()))));
                originFee.add(shoppingGoodsDto.getOriginalPrice().multiply(new BigDecimal(
                        String.valueOf(shoppingGoodsDto.getGoodsCount()))));
                plateFee.add(shoppingGoodsDto.getProfit().multiply(new BigDecimal(
                        String.valueOf(shoppingGoodsDto.getGoodsCount()))));
                TbOrderGoodinfo orderGoodinfo=new TbOrderGoodinfo();
                orderGoodinfo.setGoodId(shoppingGoodsDto.getGoodsId());
                orderGoodinfo.setGoodsAttr(shoppingGoodsDto.getGoodsAttr());
                orderGoodinfo.setOrderId(orderId);
                orderGoodinfo.setNumber(shoppingGoodsDto.getGoodsCount());
                orderGoodinfos.add(orderGoodinfo);
            }
            order.setCutDown(BigDecimal.ZERO);
            order.setBuyingPrice(supplierFee);
            order.setRealPrice(supplierFee.add(plateFee).add(expressFee));
            order.setTotalPrice(originFee);
            order.setGrossProfit(plateFee);
            orders.add(order);
            orderIds.add(orderId);
        }
        saveBatch(orders,orders.size());
        orderGoodsService.saveBatch(orderGoodinfos);

        return ResultGenerator.genSuccessResult(orderIds);
    }
}
