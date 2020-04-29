package com.hailian.ylwmall.service;

import com.hailian.ylwmall.controller.vo.NewBeeMallIndexCarouselVO;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;
import com.hailian.ylwmall.entity.Carousel;

import java.util.List;

public interface CarouselService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<NewBeeMallIndexCarouselVO> getCarouselsForIndex(int number);
}
