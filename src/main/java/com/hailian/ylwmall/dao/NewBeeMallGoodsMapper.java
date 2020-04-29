package com.hailian.ylwmall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.dto.GoodsAndCompayResDTO;
import com.hailian.ylwmall.entity.StockNumDTO;
import com.hailian.ylwmall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewBeeMallGoodsMapper extends BaseMapper<TbGoodsInfo> {
    int deleteByPrimaryKey(Long goodsId);

//    int insert(NewBeeMallGoods record);

    int insertSelective(TbGoodsInfo record);

    TbGoodsInfo selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(TbGoodsInfo record);

    int updateByPrimaryKeyWithBLOBs(TbGoodsInfo record);

    int updateByPrimaryKey(TbGoodsInfo record);

    List<GoodsAndCompayResDTO> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

    List<TbGoodsInfo> selectByPrimaryKeys(List<Long> goodsIds);

    List<TbGoodsInfo> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("newBeeMallGoodsList") List<TbGoodsInfo> newBeeMallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds, @Param("msgOffline") String msgOffline, @Param("msgReject") String msgReject, @Param("sellStatus") int sellStatus);

    int checkCanOffline(Long goodsId);
}