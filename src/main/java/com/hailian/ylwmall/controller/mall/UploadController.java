package com.hailian.ylwmall.controller.mall;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hailian.ylwmall.controller.vo.FileResult;
import com.hailian.ylwmall.util.FileUtil;
import com.hailian.ylwmall.util.NewBeeMallUtils;
import com.hailian.ylwmall.util.ResponseUtils;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Api(value = "文件上传下载接口", tags = {"文件上传下载接口"})
@Slf4j
@Controller
public class UploadController {

    @ApiOperation(value = "文件上传")
    @PostMapping({"/upload/file"})
    @ResponseBody
    public Result upload(HttpServletRequest httpServletRequest,
                         @RequestParam("file") MultipartFile file) throws URISyntaxException {
       return FileUtil.upload(file);
    }

    @ApiOperation(value = "文件下载")
    @GetMapping("/download/pic/{fileId}")
    public void download(@PathVariable String fileId ,
                         HttpServletRequest request, HttpServletResponse res){
        Result result=null;
        try{
            result = FileUtil.findFile(fileId);
        }catch (Exception e){
            log.error("Exception:{}",e);
        }

        if(result == null){
            result=ResultGenerator.genFailResult("获取文件失败");
            ResponseUtils.backData(res, JSON.toJSONString(result));
            return;
        }else if(result.getResultCode()!=200){
            ResponseUtils.backData(res, JSON.toJSONString(result));
            return;
        }else if(result.getData()==null){
            result=ResultGenerator.genFailResult("获取文件为空");
            ResponseUtils.backData(res, JSON.toJSONString(result));
            return;
        }

        if(result.getResultCode()==200){
            JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
            JSONObject dataJson=JSONObject.parseObject(jsonObject.get("data").toString());
            FileResult fileResult= JSONObject
                    .toJavaObject(dataJson, FileResult.class);
            res.setContentType("image/"+fileResult.getContentType());
            OutputStream stream = null;
            try {
                stream = res.getOutputStream();
                stream.write( fileResult.getFileBytes());
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("1-IOException:{}",e);
            } finally {
                if(stream !=null){
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error("2-IOException:{}",e);
                    }
                }
            }
        }
        return;
    }
    @RequestMapping(value = "/downLoadFile", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String downLoadFile( @RequestParam(required = true) String fileUUID, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        FileResult fileResult = new FileResult();
        //fileResult = hfsClientService.findFile(fileUUID);
        String filename = fileResult.getFileName();
        response.setContentType("multipart/form-data");
//        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Type", "text/html; charset=utf-8");

        response.addHeader("Cache-Control","max-age=0");
        try {
            response.addHeader("Content-Disposition", "attachment;fileName="+java.net.URLEncoder.encode(filename, "UTF-8"));
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(fileResult.getFileBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        try {
//            if(fileResult != null && fileResult.getFileName() != null) {
//                headers.setContentDispositionFormData("attachment", java.net.URLEncoder.encode(fileResult.getFileName(), "GBK"));
//                headers.setCacheControl("max-age=0");
//            }else{
//                throw new Exception("文件查询为空");
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<byte[]>(fileResult.getFileBytes(),headers, HttpStatus.CREATED);
    }

}
