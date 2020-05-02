package com.hailian.ylwmall.dao;

import com.hailian.ylwmall.dto.ShoppingGoodsDto;
import com.hailian.ylwmall.entity.TbShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 19012964
 * @since 2020-05-02
 */
public interface TbShoppingCartDao extends BaseMapper<TbShoppingCart> {
    List<ShoppingGoodsDto> queryShoppingGoods(@Param("userId") Long userId);
}
