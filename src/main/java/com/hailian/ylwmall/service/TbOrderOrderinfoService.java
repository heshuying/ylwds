package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.BuyFormDto;
import com.hailian.ylwmall.dto.OrderFormDto;
import com.hailian.ylwmall.dto.OrderSubmitDto;
import com.hailian.ylwmall.dto.OrderUpdateDto;
import com.hailian.ylwmall.entity.TbOrderOrderinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hailian.ylwmall.util.Result;

import java.util.Map;

/**
 * <p>
 * 订单表（主） 服务类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-02
 */
public interface TbOrderOrderinfoService extends IService<TbOrderOrderinfo> {
    Result confirmOrder(Long userId, OrderFormDto dto);
    Result getOrderInfo(Long orderNo);
    Result doOrder(Long userId, OrderSubmitDto dto);

    Result getOrders(Map<String, Object> params);

    Result updateOrderStatus(Long userId, OrderUpdateDto dto);
}
