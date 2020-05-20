package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hailian.ylwmall.common.OrderStatusEnum;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.dto.BuyFormDto;
import com.hailian.ylwmall.dto.BuyRespDto;
import com.hailian.ylwmall.dto.MyOrderRespDto;
import com.hailian.ylwmall.dto.OrderDetailDto;
import com.hailian.ylwmall.dto.OrderFormDto;
import com.hailian.ylwmall.dto.OrderRespDto;
import com.hailian.ylwmall.dto.OrderSubmitDto;
import com.hailian.ylwmall.dto.OrderUpdateDto;
import com.hailian.ylwmall.dto.ShoppingGoodsDto;
import com.hailian.ylwmall.dto.UserListDto;
import com.hailian.ylwmall.entity.StockNumDTO;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.entity.TbOrderGoodinfo;
import com.hailian.ylwmall.entity.TbOrderOrderinfo;
import com.hailian.ylwmall.dao.TbOrderOrderinfoDao;
import com.hailian.ylwmall.entity.TbUser;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.service.TbOrderGoodinfoService;
import com.hailian.ylwmall.service.TbOrderOrderinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.service.TbShoppingCartService;
import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.Const;
import com.hailian.ylwmall.util.DateTimeUtil;
import com.hailian.ylwmall.util.Query;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import com.hailian.ylwmall.util.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private TbShoppingCartService shoppingCartService;
    @Autowired
    private TbUserService userService;

    @Override
    public Result confirmOrder(Long userId, OrderFormDto dto) {
        if(dto==null||dto.getGoods()==null){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        List<TbGoodsInfo> goodsInfos=goodsService.list(
                new QueryWrapper<TbGoodsInfo>().in("goods_id",
                        dto.getGoods().stream().map(m->m.getGoodsId()).collect(Collectors.toList()))
        );
        OrderRespDto respDto=new OrderRespDto();
        List<ShoppingGoodsDto> list=new ArrayList<>();
        BigDecimal total=BigDecimal.ZERO;
        BigDecimal expressFee=BigDecimal.ZERO;
        for(BuyFormDto buyFormDto:dto.getGoods()){
            ShoppingGoodsDto shoppingGoodsDto=new ShoppingGoodsDto();
            BeanUtils.copyProperties(buyFormDto, shoppingGoodsDto);
            shoppingGoodsDto.setGoodsCount(buyFormDto.getGoodsNum());
            TbGoodsInfo current=goodsInfos.stream().filter(
                    m->buyFormDto.getGoodsId().compareTo(m.getGoodsId())==0).findAny().orElse(null);
            if(current!=null){
                BeanUtils.copyProperties(current, shoppingGoodsDto);
                shoppingGoodsDto.setSupplierId(current.getCreateUser());
            }
            list.add(shoppingGoodsDto);
            total=total.add(shoppingGoodsDto.getPrice().multiply(new BigDecimal(
                    String.valueOf(shoppingGoodsDto.getGoodsCount()))));
            expressFee=expressFee.add(shoppingGoodsDto.getTransitMoney().multiply(new BigDecimal(
                    String.valueOf(shoppingGoodsDto.getGoodsCount()))));
        }
        respDto.setTotal(total.add(expressFee));
        respDto.setExpressFee(expressFee);
        respDto.setList(list);
        return ResultGenerator.genSuccessResult(respDto);
    }

    @Override
    public Result getOrderInfo(Long orderNo) {
        OrderDetailDto orderDetailDto=new OrderDetailDto();
        TbOrderOrderinfo orderInfo=baseMapper.selectById(orderNo);
        List<TbOrderGoodinfo> orderGoods=orderGoodsService.list(
                new QueryWrapper<TbOrderGoodinfo>().eq("order_id",orderNo)
        );
        List<TbGoodsInfo> goodsInfos=goodsService.list(
                new QueryWrapper<TbGoodsInfo>().in("goods_id",
                        orderGoods.stream().map(m->m.getGoodId()).collect(Collectors.toList()))
        );
        orderDetailDto.setPayType(orderInfo.getPayType());
        orderDetailDto.setTotal(orderInfo.getRealPrice());
        orderDetailDto.setExpressFee(orderInfo.getDeliveryFee());
        orderDetailDto.setOrderNo(orderInfo.getId());
        List<ShoppingGoodsDto> list=new ArrayList<>();
        for (TbOrderGoodinfo orderGoodinfo:orderGoods){
            ShoppingGoodsDto goodsDto=new ShoppingGoodsDto();
            TbGoodsInfo goodsInfo=goodsInfos.stream().filter(
                    m->m.getGoodsId().compareTo(orderGoodinfo.getGoodId())==0
            ).findAny().orElse(null);
            if(goodsInfo!=null){
                BeanUtils.copyProperties(goodsInfo, goodsDto);
                goodsDto.setSupplierId(goodsInfo.getCreateUser());
            }
            goodsDto.setGoodsAttr(orderGoodinfo.getGoodsAttr());
            goodsDto.setGoodsCount(orderGoodinfo.getNumber());
            goodsDto.setTotal(goodsDto.getPrice()
                    .multiply(new BigDecimal(String.valueOf(goodsDto.getGoodsCount()) )));
            list.add(goodsDto);

        }
        orderDetailDto.setList(list);
        return ResultGenerator.genSuccessResult(orderDetailDto);
    }

    @Transactional
    @Override
    public Result doOrder(Long userId, OrderSubmitDto dto) {
        //根据供应商分开产生订单，目前只支持一个供应商
        OrderRespDto buyRespDto=dto.getBuyGoods();
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
            order.setUpdateTime(new Date());
            order.setCustomerId(userId);
            order.setSupplierId(supplier);
            order.setDeliveryId(dto.getDeliveryId());
            order.setStatus(OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus());
            order.setPayType(dto.getPayType());
            //供应商总价
            BigDecimal supplierFee=BigDecimal.ZERO;
            BigDecimal expressFee=BigDecimal.ZERO;
            BigDecimal plateFee=BigDecimal.ZERO;
            for(ShoppingGoodsDto shoppingGoodsDto:currentSupplierGoods){
                supplierFee=supplierFee.add(shoppingGoodsDto.getSellingPrice().multiply(new BigDecimal(
                        String.valueOf(shoppingGoodsDto.getGoodsCount()))));
                expressFee=expressFee.add(shoppingGoodsDto.getTransitMoney().multiply(new BigDecimal(
                        String.valueOf(shoppingGoodsDto.getGoodsCount()))));
                plateFee=plateFee.add(shoppingGoodsDto.getProfit().multiply(new BigDecimal(
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
            order.setTotalPrice(supplierFee.add(plateFee).add(expressFee));
            order.setGrossProfit(plateFee);
            order.setDeliveryFee(expressFee);
            orders.add(order);
            orderIds.add(orderId);
        }
        saveBatch(orders,orders.size());
        orderGoodsService.saveBatch(orderGoodinfos);
        //清理购物车
        shoppingCartService.cleanShoppingCart(userId,
                orderGoods.stream().map(m->m.getGoodsId()).collect(Collectors.toList()) );

        return ResultGenerator.genSuccessResult(orderIds);
    }

    @Override
    public Result getOrders(Map<String, Object> params) {
        QueryWrapper<TbOrderOrderinfo> wrapper=new QueryWrapper<>();
        wrapper.eq("customer_id",params.get("userId"));
        String orderNo="", status="";
        if(params.containsKey("orderNo")){
            orderNo=String.valueOf(params.get("orderNo"));
            if(StringUtils.isNotBlank(orderNo)){
                wrapper.eq("id",Long.parseLong(orderNo));
            }
        }
        if(params.containsKey("status")){
            status=String.valueOf(params.get("status"));
            if(StringUtils.isNotBlank(status)){
                wrapper.eq("status",Integer.valueOf(status));
            }
        }

        IPage<TbOrderOrderinfo> orders=baseMapper.selectPage(
                new Query<TbOrderOrderinfo>().getPage(params),
                wrapper
        );
        IPage<MyOrderRespDto> pages=new Page<>();
        List<MyOrderRespDto> myOrders=new ArrayList<>();

        pages.setCurrent(orders.getCurrent());
        pages.setTotal(orders.getTotal());
        pages.setPages(orders.getPages());
        pages.setSize(orders.getSize());
        List<TbOrderOrderinfo> list=orders.getRecords();
        if(list!=null&&list.size()>0){
            List<Long> orderIds=list.stream().map(m->m.getId()).collect(Collectors.toList());
            List<TbOrderGoodinfo> orderGoods=orderGoodsService.list(
                    new QueryWrapper<TbOrderGoodinfo>().in("order_id",orderIds)
            );
            List<TbGoodsInfo> goodsInfos=goodsService.list(
                    new QueryWrapper<TbGoodsInfo>().in("goods_id",
                            orderGoods.stream().map(m->m.getGoodId()).collect(Collectors.toList()))
            );
            List<Long> supplierIds=list.stream().map(m->m.getSupplierId())
                    .distinct().collect(Collectors.toList());
            List<TbUser> users=userService.list(
                    new QueryWrapper<TbUser>()
                            .in("user_id",supplierIds)
            );
            for (TbOrderOrderinfo current :list
                 ) {
                MyOrderRespDto myOrder=new MyOrderRespDto();
                BeanUtils.copyProperties(current,myOrder);
                myOrder.setCreateDate(DateTimeUtil.format(current.getCreateTime()));
                Const.OrderStatus orderStatus=Const.OrderStatus.getByKey(current.getStatus());
                myOrder.setStatusDesc(orderStatus.getCustomerDesc());
                TbUser currSupplier=users.stream().filter(m->current.getSupplierId()
                        .compareTo(m.getUserId())==0).findAny().get();
                myOrder.setSupplierName(currSupplier.getLoginName());
                List<TbOrderGoodinfo> currOrderGoods=orderGoods.stream().filter(
                       m-> current.getId().compareTo(m.getOrderId())==0
                ).collect(Collectors.toList());
                List<ShoppingGoodsDto> myOrderGoods=new ArrayList<>();
                for (TbOrderGoodinfo orderGood:currOrderGoods
                     ) {
                    ShoppingGoodsDto good=new ShoppingGoodsDto();
                    good.setGoodsCount(orderGood.getNumber());
                    good.setGoodsAttr(orderGood.getGoodsAttr());
                    good.setHasComment(orderGood.getHasComment());
                    good.setId(orderGood.getId());
                    TbGoodsInfo goodsInfo=goodsInfos.stream().filter(
                            m->m.getGoodsId().compareTo(orderGood.getGoodId())==0
                    ).findAny().orElse(null);
                    if(goodsInfo!=null){
                        BeanUtils.copyProperties(goodsInfo, good);
                        good.setSupplierId(goodsInfo.getCreateUser());
                        good.setTotal(good.getPrice().multiply(new BigDecimal(
                                String.valueOf(good.getGoodsCount()))));
                    }
                    myOrderGoods.add(good);
                }
                myOrder.setList(myOrderGoods);
                myOrders.add(myOrder);

            }
            pages.setRecords(myOrders);
        }else{
            pages.setRecords(myOrders);
        }
        return ResultGenerator.genSuccessResult(pages);
    }

    @Override
    public Result updateOrderStatus(Long userId, OrderUpdateDto dto) {
        if(dto==null||dto.getOrderNo()==null){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        if(Const.OrderStatus.Cancle.getKey()==dto.getStatus()
                ||Const.OrderStatus.UnSettle.getKey()==dto.getStatus()) {
            TbOrderOrderinfo orderOrderinfo = new TbOrderOrderinfo();
            orderOrderinfo.setId(dto.getOrderNo());
            orderOrderinfo.setStatus(dto.getStatus());
            orderOrderinfo.setUpdateTime(new Date());
            if(Const.OrderStatus.UnSettle.getKey()==dto.getStatus()){
                orderOrderinfo.setConfirmDate(new Date());
            }
            baseMapper.updateById(orderOrderinfo);
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
    }
}
