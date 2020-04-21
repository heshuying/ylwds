package ltd.newbee.mall.service;

import ltd.newbee.mall.controller.vo.*;
import ltd.newbee.mall.dto.CreatePayQrcodeTo;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.entity.order.CutDownPriceParam;
import ltd.newbee.mall.entity.order.DeliverGoodsParam;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


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
