package com.hailian.ylwmall.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 01439613
 */
@Slf4j
public class HttpUtils {

    public static String doPost(String urlStr, String param) throws IOException {
        return doPost(urlStr, param.getBytes(), null);
    }

    public static String doPost(String urlStr, String param, ContentType contentType) throws IOException {
        return doPost(urlStr, param.getBytes(), contentType);
    }

    public static String doPost(String urlStr, String param, String charsetName) throws IOException {
        return doPost(urlStr, param.getBytes(charsetName), null);
    }

    private static String doPost(String urlStr, byte[] param, ContentType contentType) throws IOException {
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "close");
            if (contentType != null) {
                conn.setRequestProperty("Content-Type", contentType.toString());
            }
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(5000);

            conn.connect();

            os = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            dos.write(param);
            dos.flush();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            int r = bis.read();
            while (r >= 0) {
                bos.write(r);
                r = bis.read();
            }
            String outputXml = new String(bos.toByteArray(), "UTF-8");

            return outputXml;

        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 使用httpclient
     * @param url
     * @param paramsJson
     * @return
     */
    public static String httpPostRequest(String url, JSONObject paramsJson) {
        String result = "";
        try {
            // 创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建httpPost远程连接实例
            HttpPost httpPost = new HttpPost(url);
            // 配置请求参数实例   设置连接主机服务超时时间   设置连接请求超时时间   设置读取数据连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(30000)
                    .build();
            // 为httpPost实例设置配置
            httpPost.setConfig(requestConfig);
            // 设置请求头
            httpPost.addHeader("content-type", "application/json;charset=utf-8");
            // 封装post请求参数
            httpPost.setEntity(new StringEntity(paramsJson.toJSONString(), Charset.forName("UTF-8")));
            // httpClient对象执行post请求,并返回响应参数对象
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            result = EntityUtils.toString(httpResponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            log.error("URLUtil.httpPostRequest encounters an UnsupportedEncodingException : {}", e);
        } catch (IOException e) {
            log.error("URLUtil.httpPostRequest encounters an IOException : {}", e);
        }
        log.info("URLUtil.httpPostRequest -----result----: " + result);

        return result;
    }

    /**
     * 使用httpclient 带文件的表单
     * @param url
     * @param params
     * @param data
     * @return
     */
    public static String httpPostRequest(String url,  Map<String, String> params, byte[] data) {
        log.info("params:" + JSON.toJSONString(params));
        String result = "";
        try {
            // 创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建httpPost远程连接实例
            HttpPost httpPost = new HttpPost(url);
            // 配置请求参数实例
//            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)// 设置连接主机服务超时时间
//                    .setConnectionRequestTimeout(10000)// 设置连接请求超时时间
//                    .setSocketTimeout(30000)// 设置读取数据连接超时时间
//                    .build();
//            // 为httpPost实例设置配置
//            httpPost.setConfig(requestConfig);
            // 设置请求头
            httpPost.addHeader("content-type", "multipart/form-data");
            // 封装post请求参数
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//            multipartEntityBuilder.addBinaryBody("file", new ByteArrayInputStream(data));
            multipartEntityBuilder.addBinaryBody("file", new File("D:/y.jpg"));
            params.forEach((k,v) -> {
                multipartEntityBuilder.addTextBody(k, v);
            });
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httpPost.setEntity(httpEntity);
            // httpClient对象执行post请求,并返回响应参数对象
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            result = EntityUtils.toString(httpResponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            log.error("URLUtil.httpPostRequest encounters an UnsupportedEncodingException : {}", e);
        } catch (IOException e) {
            log.error("URLUtil.httpPostRequest encounters an IOException : {}", e);
        }
        log.info("URLUtil.httpPostRequest -----result----: " + result);

        return result;
    }

    /**
     * 使用httpclient
     * headerAuthorization 有特殊的请求需要
     * 下载二进制数据
     * @param url
     * @param headerAuthorization
     * @return
     */
    public static String httpGetRequest(String url, String headerAuthorization) {
        String result = null;
        try {
            // 创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建httpPost远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 配置请求参数实例  设置连接主机服务超时时间  设置连接请求超时时间  设置读取数据连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(30000)
                    .build();
            httpGet.setConfig(requestConfig);
            // 设置请求头
            httpGet.addHeader("content-type", "application/json;charset=utf-8");
//            httpGet.addHeader("Authorization", headerAuthorization);

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            // 从响应对象中获取响应内容
            result = EntityUtils.toString(httpResponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            log.error("URLUtil.httpPostRequest encounters an UnsupportedEncodingException : {}", e);
        } catch (IOException e) {
            log.error("URLUtil.httpPostRequest encounters an IOException : {}", e);
        }
//        log.info("URLUtil.httpPostRequest -----result----: " + result);

        return result;
    }

    /**
     * 使用httpclient
     * headerAuthorization 有特殊的请求需要
     * 下载二进制数据
     * @param url
     * @param headerAuthorization
     * @return
     */
    public static HttpEntity httpDownloadRequest(String url, String headerAuthorization) {
        HttpEntity httpResponseEntity = null;
        try {
            // 创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建httpPost远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 配置请求参数实例  设置连接主机服务超时时间  设置连接请求超时时间  设置读取数据连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(30000)
                    .build();
            httpGet.setConfig(requestConfig);
            // 设置请求头
//            httpGet.addHeader("content-type", "application/json;charset=utf-8");
            httpGet.addHeader("Authorization", headerAuthorization);

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            // 从响应对象中获取响应内容
            httpResponseEntity = httpResponse.getEntity();

        } catch (UnsupportedEncodingException e) {
            log.error("URLUtil.httpPostRequest encounters an UnsupportedEncodingException : {}", e);
        } catch (IOException e) {
            log.error("URLUtil.httpPostRequest encounters an IOException : {}", e);
        }
//        log.info("URLUtil.httpPostRequest -----result----: " + result);

        return httpResponseEntity;
    }
}
