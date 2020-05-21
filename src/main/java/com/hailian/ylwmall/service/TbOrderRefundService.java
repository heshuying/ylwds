package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.RefundApplyDto;
import com.hailian.ylwmall.dto.RefundCheckDto;
import com.hailian.ylwmall.dto.RefundDeliveryDto;
import com.hailian.ylwmall.dto.RefundStatusDto;
import com.hailian.ylwmall.entity.TbOrderRefund;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hailian.ylwmall.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-20
 */
public interface TbOrderRefundService extends IService<TbOrderRefund> {
    /**
     * 发起退货
     * @param userId
     * @param dto
     * @return
     */
    Result refundApply(Long userId, RefundApplyDto dto);

    /**
     * 退货详情
     * @param orderGoodsId
     * @return
     */
    Result refundInfo(Long orderGoodsId);

    /**
     * 退货发货
     * @param dto
     * @return
     */
    Result refundDelivery(RefundDeliveryDto dto);

    /**
     * 更新退货状态
     * @param dto
     * @return
     */
    Result updateRefundStatus(RefundStatusDto dto);

    /**
     * 计算退货金额
     * @param orderGoodsId
     * @return
     */
    Result calRefundAmount(Long orderGoodsId, Integer refundNum);

    /**
     * 更新退货状态
     * @param dto
     * @return
     */
    Result checkRefund(Long userId, RefundCheckDto dto);
}
