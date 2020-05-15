package com.hailian.ylwmall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hailian.ylwmall.common.OrderStatusEnum;
import com.hailian.ylwmall.controller.vo.OrderGoodInfoVo;
import com.hailian.ylwmall.controller.vo.OrderInfoVo;
import com.hailian.ylwmall.dao.*;
import com.hailian.ylwmall.entity.TbUserAddr;
import com.hailian.ylwmall.util.Const;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import com.hailian.ylwmall.entity.order.CutDownPriceParam;
import com.hailian.ylwmall.entity.order.DeliverGoodsParam;
import com.hailian.ylwmall.entity.order.OrderInfo;
import com.hailian.ylwmall.exception.BusinessException;
import com.hailian.ylwmall.service.NewBeeMallOrderService;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NewBeeMallOrderServiceImpl implements NewBeeMallOrderService {

    @Autowired
    private NewBeeMallOrderMapper newBeeMallOrderMapper;
    @Autowired
    private NewBeeMallOrderItemMapper newBeeMallOrderItemMapper;
    @Autowired
    private NewBeeMallShoppingCartItemMapper newBeeMallShoppingCartItemMapper;
    @Autowired
    private TbGoodsInfoMapper newBeeMallGoodsMapper;
    @Autowired
    private TbUserAddrDao userAddrDao;


    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderGoodInfoMapper orderGoodInfoMapper;

    @Autowired
    private TbUserDao userMapper;

    @Override
    public PageResult getMyOrdersForSupplier(PageQueryUtil pageUtil) {
        //获取总条数
        Integer total = orderInfoMapper.countForSupplier(pageUtil);
        //查询每页的数据
        List<OrderInfo> orderInfos = orderInfoMapper.selectByPageForSupplier(pageUtil);
        List<OrderInfoVo> orderListVOS = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(OrderInfo info : orderInfos){
            String s = JSONObject.toJSONString(info);
            OrderInfoVo vo = JSONObject.parseObject(s, OrderInfoVo.class);
            //拼装收获地址
            if(info.getDeliveryId() != null){
                TbUserAddr tbUserAddr = userAddrDao.selectById(info.getDeliveryId());
                vo.setDeliveryAddress(tbUserAddr.getAcceptor()+tbUserAddr.getPhone()+"/"+tbUserAddr.getProvince()+tbUserAddr.getCity()+tbUserAddr.getArea()+tbUserAddr.getDetail());
            }
            vo.setCreateTimeString(sdf.format(vo.getCreateTime()));
            vo.setUpdateTimeString(sdf.format(vo.getUpdateTime()));
            vo.setStatus(Const.OrderStatus.getByKey(info.getStatus()).getSupplierDesc());
            //查询订单的商品信息
            List<OrderGoodInfoVo> orderGoodInfos = orderGoodInfoMapper.selectByOrderId(info.getId());
            vo.setGoods(orderGoodInfos);
            //查询用户对应的公司名
            String customerName = userMapper.queryCompanyNameById(vo.getCustomerId());
            vo.setCustomerName(customerName);
            String supplierName = userMapper.queryCompanyNameById(vo.getSupplierId());
            vo.setSupplierName(supplierName);
            orderListVOS.add(vo);
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    /**
     *  资源方-导出订单
     */
    public void exportOrdersForSupplier(){
    }

    @Override
    public PageResult getMyOrdersForPlatform(PageQueryUtil pageUtil) {
        //获取总条数
        int total = orderInfoMapper.countForPlatform(pageUtil);
        //查询每页的数据
        List<OrderInfo> orderInfos = orderInfoMapper.selectByPageForPlatform(pageUtil);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<OrderInfoVo> orderListVOS = new ArrayList<>();
        for(OrderInfo info : orderInfos){
            String s = JSONObject.toJSONString(info);
            OrderInfoVo vo = JSONObject.parseObject(s, OrderInfoVo.class);
            vo.setCreateTimeString(sdf.format(vo.getCreateTime()));
            vo.setUpdateTimeString(sdf.format(vo.getUpdateTime()));
            vo.setStatus(Const.OrderStatus.getByKey(info.getStatus()).getPlateDesc());
            //查询订单的商品信息
            List<OrderGoodInfoVo> orderGoodInfos = orderGoodInfoMapper.selectByOrderId(info.getId());
            vo.setGoods(orderGoodInfos);
            //查询用户对应的公司名
            String customerName = userMapper.queryCompanyNameById(vo.getCustomerId());
            vo.setCustomerName(customerName);
            String supplierName = userMapper.queryCompanyNameById(vo.getSupplierId());
            vo.setSupplierName(supplierName);
            orderListVOS.add(vo);
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public void deliverGoods(DeliverGoodsParam params) {
        OrderInfo info = new OrderInfo();
        info.setId(params.getOrderId());
        info.setExpressCompany(params.getExpressCompany());
        info.setExpressId(params.getExpressNumber());
        info.setUpdateTime(new Date());
        info.setExpressCode(params.getExpressCode());
        info.setStatus(3);
        orderInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public void cutDownPrice(CutDownPriceParam params) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(params.getOrderId());
        BigDecimal totalPrice = orderInfo.getTotalPrice();
        BigDecimal buyingPrice = orderInfo.getBuyingPrice();
        if(totalPrice.subtract(buyingPrice).compareTo(params.getCutdownNumber()) == -1){
            throw new BusinessException("减免金额不能超过利润额");
        }
        OrderInfo info = new OrderInfo();
        info.setId(params.getOrderId());
        info.setCutDown(params.getCutdownNumber());

        int i = orderInfoMapper.updateByPrimaryKeySelective(info);
        int i1 = orderInfoMapper.cutDownPrice(params);
        if(i == 0 || i1 == 0){
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public void batchDeliverGoods(InputStream inputStream) throws Exception {
        List<OrderInfoVo> orderInfoVos = excelToList(inputStream);
        for(OrderInfoVo vo : orderInfoVos){
            if(StringUtils.isBlank(vo.getExpressCompany())){
                throw new BusinessException("快递公司必填");
            }
            if(StringUtils.isBlank(vo.getExpressId())){
                throw new BusinessException("快递单号必填");
            }
            OrderInfo info = new OrderInfo();
            info.setId(vo.getId());
            info.setExpressCompany(vo.getExpressCompany());
            info.setExpressId(vo.getExpressId());
            info.setUpdateTime(new Date());
            info.setStatus(3);
            orderInfoMapper.updateByPrimaryKeySelective(info);
        }
    }

    private List<OrderInfoVo> excelToList(InputStream inputStream) throws Exception{
        List<OrderInfoVo> list = new ArrayList<>();
        jxl.Workbook book = Workbook.getWorkbook(inputStream);
        Sheet sheet = book.getSheet(0);
        for (int j = 1; j < sheet.getRows(); j++) {
            Cell[] cells = sheet.getRow(j);
            OrderInfoVo vo = new OrderInfoVo();
            for(int k=0;k<cells.length;k++){
                if(k == 0){
                    vo.setId(Long.valueOf(cells[k].getContents()));
                }
                if(k == 5){
                    vo.setExpressCompany(cells[k].getContents());
                }
                if(k==6){
                    vo.setExpressId(cells[k].getContents());
                }
            }
            list.add(vo);
        }
        return list;
    }



    /**
     * 将list集合转成Excel文件
     * @param list  对象集合
     * @param outputStream  输出路径
     * @return   返回文件路径
     */
    public String createExcel(List<? extends Object> list, ServletOutputStream outputStream){
        String result = "";
        if(list.size()==0||list==null){
            result = "没有对象信息";
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object o = list.get(0);
            Class<? extends Object> clazz = o.getClass();
            String className = clazz.getSimpleName();
            Field[] fields=clazz.getDeclaredFields();    //这里通过反射获取字段数组
            String fileName = "orders";
            String name = fileName.concat(".xls");
            WritableWorkbook book = null;
            File file = null;
            try {
                WorkbookSettings workbookSettings = new WorkbookSettings();
                workbookSettings.setEncoding("UTF-8"); //关键代码，解决中文乱码
                book = Workbook.createWorkbook(outputStream);  //创建xls文件
                WritableSheet sheet  =  book.createSheet(className,0);
                int i = 0;  //列
                int j = 0;  //行
                for(Field f:fields){
                    if(!f.getName().equals("createTimeString") && !f.getName().equals("updateTimeString") &&
                            !f.getName().equals("customerId") && !f.getName().equals("supplierI")){
                        j = 0;
                        Label label = new Label(i, j,getRealName(f.getName()));   //这里把字段名称写入excel第一行中
                        sheet.addCell(label);
                        j = 1;
                        for(Object obj:list){
                            Object temp = getFieldValueByName(f.getName(),obj);
                            String strTemp = "";
                            if(temp!=null){
                                if(f.getName().equals("createTime") || f.getName().equals("updateTime")){
                                    strTemp = new String(sdf.format((Date) temp).getBytes("utf-8"),"utf-8");
                                }else {
                                    strTemp = temp.toString();
                                }
                            }
                            sheet.addCell(new Label(i,j,strTemp));  //把每个对象此字段的属性写入这一列excel中
                            j++;
                        }
                        i++;
                    }

                }
                book.write();
            } catch (Exception e) {
                result = "SystemException";
                e.printStackTrace();
            }finally{
                fileName = null;
                name = null;
                file = null;
                if(book!=null){
                    try {
                        book.close();
                    } catch (WriteException e) {
                        result = "WriteException";
                        e.printStackTrace();
                    } catch (IOException e) {
                        result = "IOException";
                        e.printStackTrace();
                    }
                }
            }

        }

        return result;   //最后输出文件路径
    }




    /**
     * 获取属性值
     * @param fieldName 字段名称
     * @param o 对象
     * @return  Object
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);    //获取方法名
            Method method = o.getClass().getMethod(getter, new Class[] {});  //获取方法对象
            Object value = method.invoke(o, new Object[] {});    //用invoke调用此对象的get字段方法
            return value;  //返回值
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRealName(String name){
        switch (name){
            case "id" : return "订单编号";
            case "status" : return "订单状态";
            case "updateTime" : return "最后更新时间";
            case "createTime" : return "创建时间";
            case "deliveryAddress" : return "收获地址";
            case "expressCompany" : return "快递公司";
            case "expressId" : return "快递单号";
            case "customerId" : return "客户id";
            case "supplierId" : return "商户id";
            case "customerName" : return "客户名称";
            case "supplierName" : return "商户名称";
            case "goods" : return "商品详情";
            case "totalPrice" : return "订单原价";
            case "userRemark": return "用户备注";
            case "grossProfit": return "毛利润";
            case "buyingPrice": return "进货价";
            case "cutDown" : return "平台减免价格";
            case "realPrice" : return "最终售价";
            default:return "";
        }

    }


}