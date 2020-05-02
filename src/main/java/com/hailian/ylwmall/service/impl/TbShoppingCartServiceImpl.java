package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.dto.ShoppingCartFormDto;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.entity.TbShoppingCart;
import com.hailian.ylwmall.dao.TbShoppingCartDao;
import com.hailian.ylwmall.service.GoodsService;
import com.hailian.ylwmall.service.TbShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-02
 */
@Service
public class TbShoppingCartServiceImpl extends ServiceImpl<TbShoppingCartDao, TbShoppingCart> implements TbShoppingCartService {
    @Autowired
    private GoodsService goodsService;
    @Override
    public Result addShoppingCart(ShoppingCartFormDto dto) {
        if(dto==null
                ||dto.getGoodsId()==null||dto.getGoodsId()<=0
                ||dto.getUserId()==null||dto.getGoodsId()<=0){
            return ResultGenerator.genFailResult(ServiceResultEnum.FAIL_ILLEGAL.getResult());
        }
        //获取商品
        TbGoodsInfo goodsInfo=goodsService.getById(dto.getGoodsId());
        if(goodsInfo==null){
            return ResultGenerator.genFailResult(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        //校验库存
        if(goodsInfo.getStockNum()< dto.getGoodsNum()){
            return ResultGenerator.genFailResult(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
        }
        //添加购物车
        TbShoppingCart exits=baseMapper.selectOne(
                new QueryWrapper<TbShoppingCart>()
                        .eq("user_id",dto.getUserId())
                .eq("goods_id",dto.getGoodsId())
                .eq("goods_attr",dto.getGoodsAttr())
        );
        if(exits==null){
            //新增
            exits=new TbShoppingCart();
            BeanUtils.copyProperties(dto,exits);
            exits.setCreateTime(new Date());
            exits.setGoodsCount(dto.getGoodsNum());
            baseMapper.insert(exits);
        }else{
            //更新数量
            TbShoppingCart shoppingCart=new TbShoppingCart();
            shoppingCart.setId(exits.getId());
            shoppingCart.setGoodsCount(exits.getGoodsCount()+dto.getGoodsNum());
            baseMapper.updateById(shoppingCart);
        }

        return ResultGenerator.genSuccessResult();
    }
}
