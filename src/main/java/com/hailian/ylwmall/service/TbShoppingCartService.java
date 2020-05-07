package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.BuyFormDto;
import com.hailian.ylwmall.dto.ShoppingGoodsUpdateDto;
import com.hailian.ylwmall.entity.TbShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hailian.ylwmall.util.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-02
 */
public interface TbShoppingCartService extends IService<TbShoppingCart> {
    Result addShoppingCart(BuyFormDto dto);
    Result shoppingCarts(Long userId);

    /**
     * 删除单个购物车商品
     * @param shoppingCartId
     * @return
     */
    Result delShoppingCart(Long shoppingCartId);

    /**
     * 清理购物车商品
     * @param userId
     * @param goodsIds
     * @return
     */
    Result cleanShoppingCart(Long userId, List<Long> goodsIds);

    /**
     * 清空购物车
     * @param userId
     * @return
     */
    Result cleanShoppingCart(Long userId);

    /**
     * 更新购物车数量
     * @param updateDto
     * @return
     */
    Result updateShoppingNum(ShoppingGoodsUpdateDto updateDto);

}
