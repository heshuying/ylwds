package com.hailian.ylwmall.quartz;

import com.hailian.ylwmall.service.TbGoodsCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 * @author 19033323
 */
@Slf4j
@Component
public class MallQuartz {
    @Autowired
    TbGoodsCommentService goodsCommentService;

    /**
     * 每天凌晨1点自动评价
     */
    @Scheduled(cron="0 0 1 * * ?")
    public void autoComment(){
        log.info("自动评价定时任务start：");
        goodsCommentService.autoComment();
        log.info("自动评价定时任务end：");
    }
}
