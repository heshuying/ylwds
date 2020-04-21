package ltd.newbee.mall.dao;

import ltd.newbee.mall.controller.vo.OrderGoodInfoVo;
import ltd.newbee.mall.entity.order.OrderGoodInfo;
import ltd.newbee.mall.entity.order.OrderGoodInfoKey;
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