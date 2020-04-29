package com.hailian.ylwmall.service;

import com.hailian.ylwmall.entity.order.CutDownPriceParam;
import com.hailian.ylwmall.entity.order.DeliverGoodsParam;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;

import javax.servlet.ServletOutputStream;
import java.io.InputStream;
import java.util.List;


public interface NewBeeMallOrderService {


    /**
     * 我的订单列表 - 平台方
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrdersForPlatform(PageQueryUtil pageUtil);

    /**
     * 我的订单列表 - 资源方
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrdersForSupplier(PageQueryUtil pageUtil);


    void deliverGoods(DeliverGoodsParam params);

    void cutDownPrice(CutDownPriceParam params);

    void batchDeliverGoods(InputStream inputStream) throws Exception;

    String createExcel(List<? extends Object> list, ServletOutputStream outputStream);

}
