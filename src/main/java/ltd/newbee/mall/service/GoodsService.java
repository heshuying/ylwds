package ltd.newbee.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.dto.GoodsStatusUpdateReqDTO;
import ltd.newbee.mall.dto.UserListDto;
import ltd.newbee.mall.entity.TbGoodsInfo;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.util.List;

public interface GoodsService extends IService<TbGoodsInfo> {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveNewBeeMallGoods(TbGoodsInfo goods);

    /**
     * 批量新增商品数据
     *
     * @param newBeeMallGoodsList
     * @return
     */
    void batchSaveNewBeeMallGoods(List<TbGoodsInfo> newBeeMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateNewBeeMallGoods(TbGoodsInfo goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    TbGoodsInfo getNewBeeMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(GoodsStatusUpdateReqDTO requstBean, int sellStatus);

    boolean checkCanOffline(Long googdsId);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil);

    List<UserListDto> queryCompanyNameList();
}
