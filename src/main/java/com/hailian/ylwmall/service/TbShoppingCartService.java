package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.ShoppingCartFormDto;
import com.hailian.ylwmall.entity.TbShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hailian.ylwmall.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-02
 */
public interface TbShoppingCartService extends IService<TbShoppingCart> {
    Result addShoppingCart(ShoppingCartFormDto dto);

}
