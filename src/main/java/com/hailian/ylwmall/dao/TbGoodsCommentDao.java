package com.hailian.ylwmall.dao;

import com.hailian.ylwmall.entity.TbGoodsComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hailian.ylwmall.entity.TbOrderGoodinfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 19012964
 * @since 2020-05-13
 */
public interface TbGoodsCommentDao extends BaseMapper<TbGoodsComment> {

    List<TbOrderGoodinfo> getNotCommentList();

    Integer getScore(String goodsId);

}
