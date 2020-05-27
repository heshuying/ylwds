package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.dto.RefundApplyDto;
import com.hailian.ylwmall.dto.RefundCheckDto;
import com.hailian.ylwmall.dto.RefundDeliveryDto;
import com.hailian.ylwmall.dto.RefundStatusDto;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.entity.TbOrderGoodinfo;
import com.hailian.ylwmall.entity.TbOrderOrderinfo;
import com.hailian.ylwmall.entity.TbOrderRefund;
import com.hailian.ylwmall.dao.TbOrderRefundDao;
import com.hailian.ylwmall.entity.TbUser;
import com.hailian.ylwmall.entity.TbUserAddr;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.service.PayService;
import com.hailian.ylwmall.service.TbOrderGoodinfoService;
import com.hailian.ylwmall.service.TbOrderOrderinfoService;
import com.hailian.ylwmall.service.TbOrderRefundService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.service.TbUserAddrService;
import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.Const;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-20
 */
@Slf4j
@Service
public class TbOrderRefundServiceImpl extends ServiceImpl<TbOrderRefundDao, TbOrderRefund> implements TbOrderRefundService {
    @Autowired
    private TbOrderGoodinfoService orderGoodinfoService;
    @Autowired
    private TbOrderOrderinfoService orderinfoService;
    @Autowired
    private TbUserAddrService userAddrService;
    @Autowired
    private TbUserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private PayService payService;
    /**
     * 发起退货
     * @param userId
     * @param dto
     * @return
     */
    @Transactional
    @Override
    public Result refundApply(Long userId, RefundApplyDto dto) {
        if (dto == null || userId == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        //检查是否已发起过，如果已发起，删除历史记录
        TbOrderRefund exists=baseMapper.selectOne(new QueryWrapper<TbOrderRefund>()
        .eq("order_id", dto.getOrderId()));
        if(exists!=null&&exists.getId()!=null){
            baseMapper.deleteById(exists.getId());
        }

        TbOrderGoodinfo orderGoods = orderGoodinfoService.getById(dto.getOrderGoodsId());
        TbOrderRefund orderRefund = new TbOrderRefund();
        BeanUtils.copyProperties(dto, orderRefund);
        orderRefund.setUserId(userId);
        orderRefund.setGoodsId(orderGoods.getGoodId());
        orderRefund.setGoodsAttribute(orderGoods.getGoodsAttr());
        orderRefund.setCreateTime(new Date());
        orderRefund.setUpdateTime(new Date());
        baseMapper.insert(orderRefund);

        TbOrderGoodinfo orderGoodinfo = new TbOrderGoodinfo();
        orderGoodinfo.setId(dto.getOrderGoodsId());
        orderGoodinfo.setRefundId(orderRefund.getId());
        orderGoodinfoService.updateById(orderGoodinfo);

        //更新订单状态
        TbOrderOrderinfo tbOrderOrderinfo = new TbOrderOrderinfo();
        tbOrderOrderinfo.setId(dto.getOrderId());
        tbOrderOrderinfo.setStatus(Const.OrderStatus.Refund_Confirm.getKey());
        tbOrderOrderinfo.setUpdateTime(new Date());
        orderinfoService.updateById(tbOrderOrderinfo);
        return ResultGenerator.genSuccessResult();

    }


    @Override
    public Result refundInfo(Long orderNo) {
        TbOrderRefund tbOrderRefund=baseMapper.selectOne(new QueryWrapper<TbOrderRefund>()
                .eq("order_id", orderNo));
        TbGoodsInfo goodsInfo=goodsService.getById(tbOrderRefund.getGoodsId());
        tbOrderRefund.setGoodsName(goodsInfo==null?"":goodsInfo.getGoodsName());
        TbOrderGoodinfo orderGoods=orderGoodinfoService.getOne(new QueryWrapper<TbOrderGoodinfo>()
                .eq("refund_id", tbOrderRefund.getId()));
        tbOrderRefund.setBuyNum(orderGoods==null?0:orderGoods.getNumber());
        TbOrderOrderinfo tbOrderOrderinfo=orderinfoService.getById(orderNo);
        tbOrderRefund.setOrderAmount(tbOrderOrderinfo==null?BigDecimal.ZERO:tbOrderOrderinfo.getRealPrice());
        tbOrderRefund.setStatus(tbOrderOrderinfo.getStatus());
        tbOrderRefund.setStatusDesc(Const.OrderStatus.getByKey(tbOrderOrderinfo.getStatus()).getCustomerDesc());
        if(tbOrderRefund.getDeliveryId()>0){
            //拼接地址
            TbUserAddr userAddr=userAddrService.getById(tbOrderRefund.getDeliveryId());
            tbOrderRefund.setDeliveryAddr(userAddr.getProvince()+
                    userAddr.getCity()+userAddr.getArea()+userAddr.getDetail());
            tbOrderRefund.setAcceptor(userAddr.getAcceptor());
            tbOrderRefund.setAcceptorPhone(userAddr.getPhone());
        }
        return ResultGenerator.genSuccessResult(tbOrderRefund);
    }

    @Transactional
    @Override
    public Result refundDelivery(RefundDeliveryDto dto) {
        TbOrderRefund tbOrderRefund=baseMapper.selectById(dto.getRefundId());
        BeanUtils.copyProperties(dto,tbOrderRefund);
            tbOrderRefund.setId(dto.getRefundId());
            baseMapper.updateById(tbOrderRefund);

            //更新订单状态
            TbOrderOrderinfo tbOrderOrderinfo=new TbOrderOrderinfo();
            tbOrderOrderinfo.setId(tbOrderRefund.getOrderId());
            tbOrderOrderinfo.setStatus(Const.OrderStatus.Refund_Deliveried.getKey());
            tbOrderOrderinfo.setUpdateTime(new Date());
            orderinfoService.updateById(tbOrderOrderinfo);
            return ResultGenerator.genSuccessResult();



    }

    @Override
    public Result updateRefundStatus(RefundStatusDto dto) {
        TbOrderRefund tbOrderRefund=baseMapper.selectById(dto.getRefundId());
        //更新订单状态
        TbOrderOrderinfo tbOrderOrderinfo=new TbOrderOrderinfo();
        tbOrderOrderinfo.setId(tbOrderRefund.getOrderId());
        tbOrderOrderinfo.setStatus(dto.getStatus());
        tbOrderOrderinfo.setUpdateTime(new Date());
        orderinfoService.updateById(tbOrderOrderinfo);
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public Result calRefundAmount(Long orderGoodsId, Integer refundNum) {
        TbOrderGoodinfo currentRefundGoods = orderGoodinfoService.getById(orderGoodsId);
        if(refundNum>currentRefundGoods.getNumber()){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        log.info("计算退货金额，订单ID：{}，订单商品ID：{}，退货数量：{}",
                currentRefundGoods.getOrderId(),currentRefundGoods.getId(), refundNum
        );
        BigDecimal refundAmount=BigDecimal.ZERO;
        //判断订单是否存在减免
        TbOrderOrderinfo orderInfo=orderinfoService.getById(currentRefundGoods.getOrderId());
        if(orderInfo.getCutDown().compareTo(BigDecimal.ZERO)==0){
            //无减免
            TbGoodsInfo goodsInfo=goodsService.getById(currentRefundGoods.getGoodId());
            refundAmount=goodsInfo.getPrice().multiply(new BigDecimal(refundNum));
            log.info("计算退货金额，订单ID：{}无减免，退货金额：{}",
                    currentRefundGoods.getOrderId(), refundAmount
            );
        }else{
            //存在减免
            //获取订单所有商品
            List<TbOrderGoodinfo> orderGoods=orderGoodinfoService.list(
                    new QueryWrapper<TbOrderGoodinfo>().eq("order_id", currentRefundGoods.getOrderId())
            );
            if(orderGoods.size()==1&&refundNum==currentRefundGoods.getNumber()){
                //1个商品，全部退货
                refundAmount=orderInfo.getRealPrice();
                log.info("计算退货金额，订单ID：{},单个商品全部退货，退货金额：{}",
                        currentRefundGoods.getOrderId(), refundAmount);
            }else if(orderGoods.size()==1&&refundNum<currentRefundGoods.getNumber()){
                //1个商品，部分退货
                BigDecimal singleCoutdown=orderInfo.getCutDown().divide(
                        BigDecimal.valueOf(currentRefundGoods.getNumber()) ,2, BigDecimal.ROUND_HALF_UP
                );
                TbGoodsInfo goodsInfo=goodsService.getById(currentRefundGoods.getGoodId());

                refundAmount=goodsInfo.getPrice().multiply(BigDecimal.valueOf(refundNum)).subtract(
                        singleCoutdown.multiply(BigDecimal.valueOf(refundNum))
                ) ;
                log.info("计算退货金额，订单ID：{},单个商品部分退货，退货金额：{}",
                        currentRefundGoods.getOrderId(), refundAmount);
            }else{
                //多个商品，部分退货
                //尚未退货的商品
                List<TbOrderGoodinfo> unRefunds=orderGoods.stream().filter(
                        m->m.getRefundId()==0
                                &&m.getId()!=currentRefundGoods.getId()
                ).collect(Collectors.toList());
                if(unRefunds!=null&&unRefunds.size()>0){
                    //还存在尚未退货的商品，不采用减法
                    //当前订单商品 总额
                    TbGoodsInfo goodsInfo=goodsService.getById(currentRefundGoods.getGoodId());
                    BigDecimal currentAmount=goodsInfo.getPrice().multiply(BigDecimal.valueOf(currentRefundGoods.getNumber()));
                    //当前订单商品减免分摊金额=订单商品总额/订单金额（减免前）*减免金额
                    BigDecimal currentCoutdownAmount=currentAmount.divide(
                            orderInfo.getTotalPrice(),2, BigDecimal.ROUND_HALF_UP
                    ).multiply(orderInfo.getCutDown());

                    refundAmount=goodsInfo.getPrice().multiply(BigDecimal.valueOf(refundNum)).subtract(
                            currentCoutdownAmount.multiply(BigDecimal.valueOf(refundNum).divide(
                                    BigDecimal.valueOf(currentRefundGoods.getNumber()),2, BigDecimal.ROUND_HALF_UP
                            ))
                    ) ;
                    log.info("计算退货金额，订单ID：{},多个商品部分退货，退货金额：{}，" +
                                    "当前退货商品分摊减免金额：{}",
                            currentRefundGoods.getOrderId(), refundAmount,currentCoutdownAmount);
                }else{
                    //减法计算最后一个订单商品的减免分摊金额
                    //计算其他订单商品分摊减免金额之和
                    BigDecimal sumRefund=BigDecimal.ZERO;
                    List<TbOrderGoodinfo> refunded=orderGoods.stream().filter(
                            m->m.getId()!=currentRefundGoods.getId()
                    ).collect(Collectors.toList());
                    for(TbOrderGoodinfo tem:refunded){
                        TbGoodsInfo goodsInfo=goodsService.getById(tem.getGoodId());
                        BigDecimal currentAmount=goodsInfo.getPrice().multiply(BigDecimal.valueOf(currentRefundGoods.getNumber()));

                        sumRefund.add(currentAmount.divide(
                                orderInfo.getTotalPrice(),2, BigDecimal.ROUND_HALF_UP
                        ).multiply(orderInfo.getCutDown()));
                    }
                    //当前订单商品减免分摊金额=订单减免金额-订单其他商品的减免分摊金额
                    BigDecimal currentCoutdownAmount=orderInfo.getCutDown().subtract(sumRefund);

                    TbGoodsInfo goodsInfo=goodsService.getById(currentRefundGoods.getGoodId());
                    refundAmount=goodsInfo.getPrice().multiply(BigDecimal.valueOf(refundNum)).subtract(
                            currentCoutdownAmount.multiply(BigDecimal.valueOf(refundNum).divide(
                                    BigDecimal.valueOf(currentRefundGoods.getNumber()),2, BigDecimal.ROUND_HALF_UP
                            ))
                    ) ;
                    log.info("计算退货金额，订单ID：{},多个商品最后一个商品退货，退货金额：{}，" +
                                    "当前退货商品分摊减免金额：{}",
                            currentRefundGoods.getOrderId(), refundAmount,currentCoutdownAmount);

                }
            }

        }


        return ResultGenerator.genSuccessResult(refundAmount);
    }

    @Override
    public Result checkRefund(Long userId, RefundCheckDto dto) {

        TbOrderRefund tbOrderRefund=baseMapper.selectOne(new QueryWrapper<TbOrderRefund>()
        .eq("order_id", dto.getOrderId()));
        TbOrderRefund updateDto=new TbOrderRefund();
        updateDto.setId(tbOrderRefund.getId());
        if(Const.OrderStatus.Refund_Reject.getKey()==dto.getStatus()) {
            //驳回
            updateDto.setRejectReason(dto.getRejectReason());
            baseMapper.updateById(updateDto);
        }else if(Const.OrderStatus.Refund_Delivery.getKey()==dto.getStatus()){
            //同意退货
            updateDto.setDeliveryId(dto.getDeveryId());
            updateDto.setOpenComment(dto.getComment());
            baseMapper.updateById(updateDto);
        }else if(Const.OrderStatus.Refund_ConfirmMoney.getKey()==dto.getStatus()){
            //部分退款
            updateDto.setRejectReason(dto.getRejectReason());
            updateDto.setRefundActualAmount(tbOrderRefund.getRefundAmount()
                    .subtract(dto.getContdownAmount()));
            baseMapper.updateById(updateDto);
            payService.tradeRefund(dto.getOrderId().toString(),userId);
        }else if(Const.OrderStatus.Refunding.getKey()==dto.getStatus()){
            //全额退款
            //判断用户类型，如果是平台则不更新价格
            TbUser user=userService.getById(userId);
            if(user.getUserType().toString().equals(Const.UserType.Supplier.getKey())) {
                updateDto.setRefundActualAmount(tbOrderRefund.getRefundAmount());
                baseMapper.updateById(updateDto);
            }
            payService.tradeRefund(dto.getOrderId().toString(),userId);
        }

        //更新订单状态
        TbOrderOrderinfo tbOrderOrderinfo=new TbOrderOrderinfo();
        tbOrderOrderinfo.setId(tbOrderRefund.getOrderId());
        tbOrderOrderinfo.setStatus(dto.getStatus());
        tbOrderOrderinfo.setUpdateTime(new Date());
        orderinfoService.updateById(tbOrderOrderinfo);

        return ResultGenerator.genSuccessResult();
    }
}
