package com.hailian.ylwmall.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: FileUtil
 * @Description: TODO
 * @Author: 01495172
 * @Date: 2019/12/27
 */
@Slf4j
@Component
public class FileUtil {

    private static String baseUrl;

    private static String appName;

    private static String appKey;

    private static String appSecret;

    public static Result upload(MultipartFile file) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            //设置模式为BROWSER_COMPATIBLE，并设置字符集为UTF8
            HttpEntity entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setCharset(Charset.forName("UTF-8"))
                    .addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename())
                    .addTextBody("appName", appName)
                    .build();


            HttpPost httpPost = new HttpPost(baseUrl + "/fileopt/uploadFile");
//            httpPost.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((appKey + ": " + appSecret).getBytes()));
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            log.info("upload result:" + result);
            FileResult resultObj = JSONObject.parseObject(result, FileResult.class);

            if (0 == resultObj.getCode()) {
                return  ResultGenerator.genSuccessResult(resultObj.getData());

            } else {
                return ResultGenerator.genFailResult(resultObj.getMessage());
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

    public static Result uploadFile(File file) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpEntity entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setCharset(Charset.forName("UTF-8"))
                    .addBinaryBody("file", file)
                    .addTextBody("appName", appName)
                    .build();

            HttpPost httpPost = new HttpPost(baseUrl + "/fileopt/uploadFile");
//            httpPost.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((appKey + ": " + appSecret).getBytes()));
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            log.info("upload result:" + result);
            FileResult resultObj = JSONObject.parseObject(result, FileResult.class);
            if (0 == resultObj.getCode()) {
                return ResultGenerator.genSuccessResult(resultObj.getData());
            } else {
                return ResultGenerator.genFailResult(resultObj.getMessage());
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

    public static Result findFile(String fileUuid) {
        String data;
        try {
            String auth = "Basic " +
                    Base64.getEncoder().encodeToString((appKey + ": " + appSecret).getBytes());
            data = HttpUtils.httpGetRequest(baseUrl + "/fileopt/findFile/" + fileUuid + "?appName=" + appName + "&needData=0", auth);
        } catch (Exception e) {
            log.error("文件下载失败", e);
            return ResultGenerator.genFailResult("文件下载失败");
        }
        return ResultGenerator.genSuccessResult(data);
    }

    public static Result download(String fileUuid) {
        HttpEntity httpEntity;
        try {
            String auth = "Basic " + Base64.getEncoder().encodeToString((appKey + ": " + appSecret).getBytes());
            httpEntity = HttpUtils.httpDownloadRequest(baseUrl + "/fileopt/downloadFile/" + fileUuid + "?appName=" + appName, auth);
        } catch (Exception e) {
            log.error("文件下载失败", e);
            return ResultGenerator.genFailResult("文件下载失败");
        }
        return ResultGenerator.genSuccessResult(httpEntity);
    }

    /**
     * 创建本地临时文件
     *
     * @param fileUuid
     * @return string
     */
    public static String creatTempFile(String fileUuid, boolean again) {
        String filePath = null;
        Result fileInfo = FileUtil.findFile(fileUuid);
        if (null != fileInfo && 200 == fileInfo.getResultCode()) {
            String fileInfoStr = (String) fileInfo.getData();
            JSONObject jsonObject = JSON.parseObject(fileInfoStr);
            String data = jsonObject.getString("data");
            JSONObject dataObj = JSON.parseObject(data);
            String fileName = dataObj.getString("fileName");
            filePath = "src/main/resources/temp/" + fileName;
            Result download = FileUtil.download(fileUuid);
            try {
                FileUtil.byteToFile(((HttpEntity) download.getData()).getContent(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (again) {
            // mongo的文件服务有个问题，刚上传的不能立马查到。再来一次
            filePath = creatTempFile(fileUuid, false);
        }
        return filePath;
    }
    
    /**
     * 下载url转File
     *
     * @param url
     * @return File
     */   
	public static File urlToPdfFile(String url){
		
		//根据url获取文件流
		URL pdfurl;
		InputStream inputStream = null;
		HttpURLConnection conn = null;
		File file = null;
		String filePath = null;
		try {
			pdfurl = new URL(url);
			conn = (HttpURLConnection)pdfurl.openConnection();
			conn.setConnectTimeout(3*1000);
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			inputStream = conn.getInputStream();
			
			filePath = "src/main/resources/temp/" + System.nanoTime()+".pdf";
            file = FileUtil.byteToFile(inputStream, filePath);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
	}
    
    /**
     * 创建本地临时文件
     *
     * @param fileUuid
     * @return File
     */
    public static File creatTempFile2(String fileUuid, boolean again) {
        String filePath = null;
        Result fileInfo = FileUtil.findFile(fileUuid);
        File file = null;
        if (null != fileInfo && 200 == fileInfo.getResultCode()) {
            String fileInfoStr = (String) fileInfo.getData();
            JSONObject jsonObject = JSON.parseObject(fileInfoStr);
            String data = jsonObject.getString("data");
            JSONObject dataObj = JSON.parseObject(data);
            String fileName = dataObj.getString("fileName");
            filePath = "src/main/resources/temp/" + fileName;
            Result download = FileUtil.download(fileUuid);
            try {
            	file = FileUtil.byteToFile(((HttpEntity) download.getData()).getContent(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (again) {
            // mongo的文件服务有个问题，刚上传的不能立马查到。再来一次
            filePath = creatTempFile(fileUuid, false);
        }
        return file;
    }

    /**
     * @param inputStream 二进制数据
     * @param filePath    文件存放目录，包括文件名及其后缀，如D:\file\bike.jpg
     * @Title: byteToFile
     * @Description: 把二进制数据转成指定后缀名的文件，例如PDF，PNG等
     * @Author: Wiener
     * @Time: 2018-08-26 08:43:36
     */
    public static File byteToFile(InputStream inputStream, String filePath) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream output = null;
        File file = new File(filePath);
        try {
            bis = new BufferedInputStream(inputStream);
            // 获取文件的父路径字符串
            File path = file.getParentFile();
            if (!path.exists()) {
                log.info("文件夹不存在，创建。path={}", path);
                boolean isCreated = path.mkdirs();
                if (!isCreated) {
                    log.error("创建文件夹失败，path={}", path);
                }
            }
            fos = new FileOutputStream(file);
            // 实例化OutputString 对象
            output = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                output.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            output.flush();
        } catch (Exception e) {
            log.error("输出文件流时抛异常，filePath={}", filePath, e);
        } finally {
            try {
                bis.close();
                fos.close();
                output.close();
            } catch (IOException e0) {
                log.error("文件处理失败，filePath={}", filePath, e0);
            }
        }
        return file;
    }

    public static class FileResult implements Serializable {

        private int code;
        private String message;
        private Object data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
    
    /**
     * 图片转换成base64
     *
     * @param imgPath
     * @return String
     */
    public static String ImageToBase64(String imgPath) {
    	InputStream in = null;
    	byte[] data = null;
    	//读取图片字节数组
    	try {
    		in = new FileInputStream(imgPath);
    		data = new byte[in.available()];
    		in.read(data);
    		in.close();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	return Base64.getEncoder().encodeToString(data);
    }

    @Value("${oss.baseUrl}")
    public void setUploadUrl(String baseUrl) {
        FileUtil.baseUrl = baseUrl;
    }

    @Value("${oss.appName}")
    public void setAppName(String appName) {
        FileUtil.appName = appName;
    }

    @Value("${oss.appKey}")
    public void setAppKey(String appKey) {
        FileUtil.appKey = appKey;
    }

    @Value("${oss.appSecret}")
    public void setAppSecret(String appSecret) {
        FileUtil.appSecret = appSecret;
    }
}
