package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.order.CutDownPriceParam;
import ltd.newbee.mall.entity.order.OrderInfo;
import ltd.newbee.mall.util.PageQueryUtil;

import java.util.List;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    int count();

    List<OrderInfo> selectByPageForSupplier(PageQueryUtil queryUtil);

    List<OrderInfo> selectByPageForPlatform(PageQueryUtil queryUtil);

    List<OrderInfo> selectByPageForCustomer(PageQueryUtil queryUtil);

    int cutDownPrice(CutDownPriceParam params);
}