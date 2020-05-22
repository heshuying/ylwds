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
import com.hailian.ylwmall.entity.TbUserAddr;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.service.TbOrderGoodinfoService;
import com.hailian.ylwmall.service.TbOrderOrderinfoService;
import com.hailian.ylwmall.service.TbOrderRefundService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.service.TbUserAddrService;
import com.hailian.ylwmall.util.Const;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-20
 */
@Service
public class TbOrderRefundServiceImpl extends ServiceImpl<TbOrderRefundDao, TbOrderRefund> implements TbOrderRefundService {
    @Autowired
    private TbOrderGoodinfoService orderGoodinfoService;
    @Autowired
    private TbOrderOrderinfoService orderinfoService;
    @Autowired
    private TbUserAddrService userAddrService;
    @Autowired
    private GoodsService goodsService;
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
        TbOrderRefund tbOrderRefund=new TbOrderRefund();
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
        TbOrderGoodinfo orderGoodinfo = orderGoodinfoService.getById(orderGoodsId);
        if(refundNum>orderGoodinfo.getNumber()){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        TbGoodsInfo goodsInfo=goodsService.getById(orderGoodinfo.getGoodId());
        BigDecimal refundAmount=goodsInfo.getPrice().multiply(new BigDecimal(refundNum));
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
        }else if(Const.OrderStatus.Refund_Delivery.getKey()==dto.getStatus()){
            //同意退货
            updateDto.setDeliveryId(dto.getDeveryId());
            updateDto.setOpenComment(dto.getComment());
        }else if(Const.OrderStatus.Refund_ConfirmMoney.getKey()==dto.getStatus()){
            //部分退款
            updateDto.setRefundActualAmount(updateDto.getRefundAmount()
                    .subtract(dto.getContdownAmount()));

        }else if(Const.OrderStatus.Refund_Edit.getKey()==dto.getStatus()){
            //全额退款
            updateDto.setRefundActualAmount(updateDto.getRefundAmount());
        }
        baseMapper.updateById(updateDto);
        //更新订单状态
        TbOrderOrderinfo tbOrderOrderinfo=new TbOrderOrderinfo();
        tbOrderOrderinfo.setId(tbOrderRefund.getOrderId());
        tbOrderOrderinfo.setStatus(dto.getStatus());
        tbOrderOrderinfo.setUpdateTime(new Date());
        orderinfoService.updateById(tbOrderOrderinfo);

        return ResultGenerator.genSuccessResult();
    }
}
