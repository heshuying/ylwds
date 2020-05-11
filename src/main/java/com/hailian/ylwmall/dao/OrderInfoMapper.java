package com.hailian.ylwmall.dao;

import com.hailian.ylwmall.entity.order.CutDownPriceParam;
import com.hailian.ylwmall.entity.order.OrderInfo;
import com.hailian.ylwmall.util.PageQueryUtil;

import java.util.List;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    Integer countForSupplier(PageQueryUtil pageUtil);


    Integer countForPlatform(PageQueryUtil pageUtil);
    List<OrderInfo> selectByPageForSupplier(PageQueryUtil queryUtil);

    List<OrderInfo> selectByPageForPlatform(PageQueryUtil queryUtil);

    List<OrderInfo> selectByPageForCustomer(PageQueryUtil queryUtil);

    int cutDownPrice(CutDownPriceParam params);
}