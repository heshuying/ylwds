package com.hailian.ylwmall.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hailian.ylwmall.dto.CommentReq;
import com.hailian.ylwmall.dto.KD100Req;
import com.hailian.ylwmall.util.MD5Util;
import com.hailian.ylwmall.util.MD5UtilsKD100;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 物流查询
 */
@Slf4j
@Api(value = "物流查询接口", tags = {"物流查询接口"})
@Controller
@RequestMapping("/api/kd100")
public class KD100Controller {

    @Value("${kd100.url}")
    String kd100Url;
    @Value("${kd100.key}")
    String key;
    @Value("${kd100.customer}")
    String customer;

    /**
     * 物流轨迹查询
     */
    @ResponseBody
    @ApiOperation(value = "物流轨迹查询")
    @GetMapping("/query")
    public Result query(@ModelAttribute KD100Req reqBean, HttpServletRequest request) {
        log.info("query请求参数：{}", JSON.toJSONString(reqBean));
        if(StringUtils.isBlank(reqBean.getCom()) || StringUtils.isBlank(reqBean.getNum())){
            return ResultGenerator.genFailResult("请求参数错误");
        }

        String data;
        JSONObject tradeReq;
        try {
            data = synQueryData(reqBean.getCom(), reqBean.getNum(), "", "", "", 0);
            tradeReq = JSONObject.parseObject(data);
        } catch (Exception e) {
            log.error("快递100查询失败", e);
            return ResultGenerator.genFailResult("快递100查询失败");
        }

        return ResultGenerator.genSuccessResult(tradeReq);

    }

    /**
     * 接收快递100回调
     * @param reqBean
     * @return
     */
    @ResponseBody
    @PostMapping("/callBack")
    public Map<String,String> callBack(@RequestBody Map<String, Object> reqBean, HttpServletRequest request){
        Map<String,String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            log.info("参数名：" + parameterNames.nextElement());
        }
        log.info("请求参数：{}", JSON.toJSONString(reqBean));

        resultMap.put("result", "true");
        resultMap.put("returnCode", "200");
        resultMap.put("message", "接收成功");
        return resultMap;
    }

    /**
     * 实时查询快递单号
     *
     * @param com      快递公司编码
     * @param num      快递单号
     * @param phone    手机号
     * @param from     出发地城市
     * @param to       目的地城市
     * @param resultv2 开通区域解析功能：0-关闭；1-开通
     * @return
     */
    public String synQueryData(String com, String num, String phone, String from, String to, int resultv2) throws Exception{

        StringBuilder param = new StringBuilder("{");
        param.append("\"com\":\"").append(com).append("\"");
        param.append(",\"num\":\"").append(num).append("\"");
        param.append(",\"phone\":\"").append(phone).append("\"");
        param.append(",\"from\":\"").append(from).append("\"");
        param.append(",\"to\":\"").append(to).append("\"");
        if (1 == resultv2) {
            param.append(",\"resultv2\":1");
        } else {
            param.append(",\"resultv2\":0");
        }
        param.append("}");

        Map<String, String> params = new HashMap<String, String>();
        params.put("customer", this.customer);
        String sign = MD5UtilsKD100.encode(param + this.key + this.customer);
        params.put("sign", sign);
        params.put("param", param.toString());

        return this.post(params);
    }

    /**
     * 发送post请求
     */
    public String post(Map<String, String> params) throws Exception{
        StringBuffer response = new StringBuffer("");

        BufferedReader reader = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (builder.length() > 0) {
                    builder.append('&');
                }
                builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                builder.append('=');
                builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] bytes = builder.toString().getBytes("UTF-8");

            URL url = new URL(kd100Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(bytes);

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response.toString();
    }

}
