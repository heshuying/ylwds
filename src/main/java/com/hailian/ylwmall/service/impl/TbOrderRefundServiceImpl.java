package com.hailian.ylwmall.service.impl;

import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.dto.RefundApplyDto;
import com.hailian.ylwmall.dto.RefundDeliveryDto;
import com.hailian.ylwmall.dto.RefundStatusDto;
import com.hailian.ylwmall.entity.TbOrderGoodinfo;
import com.hailian.ylwmall.entity.TbOrderOrderinfo;
import com.hailian.ylwmall.entity.TbOrderRefund;
import com.hailian.ylwmall.dao.TbOrderRefundDao;
import com.hailian.ylwmall.entity.TbUserAddr;
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
    /**
     * 发起退货
     * @param userId
     * @param dto
     * @return
     */
    @Transactional
    @Override
    public Result refundApply(Long userId, RefundApplyDto dto) {
        if(dto==null||userId==null){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        TbOrderRefund orderRefund=new TbOrderRefund();
        BeanUtils.copyProperties(dto, orderRefund);
            orderRefund.setUserId(userId);
            orderRefund.setCreateTime(new Date());
            orderRefund.setUpdateTime(new Date());
            baseMapper.insert(orderRefund);

            TbOrderGoodinfo orderGoodinfo = new TbOrderGoodinfo();
            orderGoodinfo.setId(dto.getOrderGoodsId());
            orderGoodinfo.setRefundId(orderRefund.getId());
            orderGoodinfoService.updateById(orderGoodinfo);

            //更新订单状态
            TbOrderOrderinfo tbOrderOrderinfo=new TbOrderOrderinfo();
            tbOrderOrderinfo.setId(dto.getOrderId());
            tbOrderOrderinfo.setStatus(Const.OrderStatus.Refund_Confirm.getKey());
            tbOrderOrderinfo.setUpdateTime(new Date());
            orderinfoService.updateById(tbOrderOrderinfo);
            return ResultGenerator.genSuccessResult();

    }

    @Override
    public Result refundInfo(Long orderGoodsId) {
        TbOrderGoodinfo orderGoodinfo = orderGoodinfoService.getById(orderGoodsId);
        TbOrderOrderinfo tbOrderOrderinfo=orderinfoService.getById(orderGoodinfo.getOrderId());
        TbOrderRefund tbOrderRefund=baseMapper.selectById(orderGoodinfo.getRefundId());
        tbOrderRefund.setStatus(tbOrderOrderinfo.getStatus());
        tbOrderRefund.setStatusDesc(Const.OrderStatus.getByKey(tbOrderOrderinfo.getStatus()).getCustomerDesc());

        TbUserAddr userAddr=userAddrService.getById(tbOrderRefund.getDeliveryId());
        tbOrderRefund.setDeliveryAddr(userAddr.getProvince()+
        userAddr.getCity()+userAddr.getArea()+userAddr.getDetail());
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
}
