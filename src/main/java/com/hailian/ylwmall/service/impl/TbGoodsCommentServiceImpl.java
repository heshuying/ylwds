package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.dao.TbOrderGoodinfoDao;
import com.hailian.ylwmall.dao.TbOrderOrderinfoDao;
import com.hailian.ylwmall.entity.TbGoodsComment;
import com.hailian.ylwmall.dao.TbGoodsCommentDao;
import com.hailian.ylwmall.entity.TbOrderGoodinfo;
import com.hailian.ylwmall.entity.TbOrderOrderinfo;
import com.hailian.ylwmall.service.TbGoodsCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hailian.ylwmall.util.CommonUtil;
import com.hailian.ylwmall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-05-13
 */
@Slf4j
@Service
public class TbGoodsCommentServiceImpl extends ServiceImpl<TbGoodsCommentDao, TbGoodsComment> implements TbGoodsCommentService {
    @Autowired
    TbOrderGoodinfoDao orderGoodinfoDao;
    @Autowired
    TbOrderOrderinfoDao orderOrderinfoDao;
    @Autowired
    TbGoodsCommentDao goodsCommentDao;

    @Override
    @Transactional
    public void autoComment(){
        List<TbOrderGoodinfo> notCommentList = goodsCommentDao.getNotCommentList();
        log.info(notCommentList.toString());
        if(notCommentList != null && !notCommentList.isEmpty()){
            for(TbOrderGoodinfo item : notCommentList){
                TbGoodsComment comment = new TbGoodsComment();
                comment.setGoodsId(item.getGoodId());
                comment.setOrderId(item.getOrderId());
                comment.setContent("评价方未及时做出评价，系统默认好评！");
                comment.setScore(5);
                comment.setGoodsAttribute(item.getGoodsAttr());
                comment.setUserId(0L);
                comment.setUserName("auto");
                comment.setIsAnonymous(0);
                comment.setIsAuto(0);
                comment.setCreateTime(new Date());
                comment.setUpdateTime(new Date());
                goodsCommentDao.insert(comment);

                // 更新为已评价
                TbOrderGoodinfo orderGoodInfo = new TbOrderGoodinfo();
                orderGoodInfo.setHasComment(true);
                orderGoodinfoDao.update(orderGoodInfo, new QueryWrapper<TbOrderGoodinfo>()
                        .eq("order_id", item.getOrderId()).eq("good_id", item.getGoodId()));
            }
        }
    }


    @Override
    public Integer getScore(String goodsId){
        return goodsCommentDao.getScore(goodsId);
    }

}
