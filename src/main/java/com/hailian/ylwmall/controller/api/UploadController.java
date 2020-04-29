package com.hailian.ylwmall.controller.api;

import com.haier.openplatform.hfs.client.dto.FileResult;
import com.hailian.ylwmall.util.FileUtil;
import com.hailian.ylwmall.util.NewBeeMallUtils;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


@Controller
@RequestMapping("/api")
public class UploadController {

    @PostMapping({"/upload/file"})
    @ResponseBody
    public Result upload(HttpServletRequest httpServletRequest,
                         @RequestParam("file") MultipartFile file) throws URISyntaxException {
       return FileUtil.upload(file);
    }



}
