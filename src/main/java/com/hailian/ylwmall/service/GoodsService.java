package com.hailian.ylwmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hailian.ylwmall.dto.GoodsStatusUpdateReqDTO;
import com.hailian.ylwmall.dto.UserListDto;
import com.hailian.ylwmall.entity.StockNumDTO;
import com.hailian.ylwmall.entity.TbGoodsInfo;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;

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

    /**
     * 批量更新库存
     * @param list
     * @return
     */
    Integer updateGoodsStock(List<StockNumDTO> list);
}
