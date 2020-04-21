package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.newbee.mall.dto.GoodsAndCompayResDTO;
import ltd.newbee.mall.entity.TbGoodsInfo;
import ltd.newbee.mall.entity.StockNumDTO;
import ltd.newbee.mall.util.PageQueryUtil;
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

}