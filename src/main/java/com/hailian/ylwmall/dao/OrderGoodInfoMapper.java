package com.hailian.ylwmall.dao;

import com.hailian.ylwmall.controller.vo.OrderGoodInfoVo;
import com.hailian.ylwmall.entity.order.OrderGoodInfo;
import com.hailian.ylwmall.entity.order.OrderGoodInfoKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderGoodInfoMapper {
    int deleteByPrimaryKey(OrderGoodInfoKey key);

    int insert(OrderGoodInfo record);

    int insertSelective(OrderGoodInfo record);

    OrderGoodInfo selectByPrimaryKey(OrderGoodInfoKey key);

    int updateByPrimaryKeySelective(OrderGoodInfo record);

    int updateByPrimaryKey(OrderGoodInfo record);

    List<OrderGoodInfoVo> selectByOrderId(Long orderId);
}