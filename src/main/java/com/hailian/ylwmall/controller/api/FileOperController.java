package com.hailian.ylwmall.controller.api;

import com.hailian.ylwmall.config.FileProperties;
import com.hailian.ylwmall.dto.UploadFileResponse;
import com.hailian.ylwmall.exception.BusinessException;
import com.hailian.ylwmall.service.impl.FileService;
import com.hailian.ylwmall.util.DateTimeUtil;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Api("文件操作")
@RestController
@RequestMapping("/api/file")
public class FileOperController {
    private static final Logger logger = LoggerFactory.getLogger(FileOperController.class);

    @Autowired
    private FileService fileService;
    @Autowired
    private FileProperties fileProperties;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ApiOperation(value = "上传文件")
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file){
        String filePath = saveFile(file);

        return ResultGenerator.genSuccessResult(filePath) ;
    }

    @ApiOperation(value = "上传多文件")
    @PostMapping("/uploadMultipleFiles")
    public Result uploadMultipleFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

        if (null == files || files.isEmpty()) {
            return ResultGenerator.genFailResult("超过文件大小");
        }
        List<String> filePaths=new ArrayList<>();
        for (MultipartFile file : files) {

            if (null == file || file.isEmpty()) {
                return ResultGenerator.genFailResult( "超过文件大小");
            } else {
                String filePath = saveFile(file);
                filePaths.add(filePath);
            }
        }
        return ResultGenerator.genSuccessResult(filePaths);
    }

    @ApiOperation(value = "下载文件")
    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam  Long fileId,
                                                 @RequestParam String fileType,
                                                 HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource("");

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * 保存文件方法
     * @param file
     * @return
     */
    public String saveFile(MultipartFile file) {

        //文件后缀名
        String ext = null;
        String fileName = file.getOriginalFilename();
        if (fileName.contains(".")) {
            ext = fileName.substring(fileName.lastIndexOf("."));
        } else {
            ext = "";
        }

        //文件大小
        int size = (int) file.getSize();

        //判断文件的大小
        Long maxSize = Long.valueOf(maxFileSize.replaceAll("MB", ""));

        if (size > (maxSize * 1024 * 1024)) {
            String strArray[] = {maxFileSize};
            throw new BusinessException("超过文件大小");
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String date= DateTimeUtil.format(new Date(),"yyyyMMdd");
        String filePath = fileProperties.getUploadDir() + date +  "/" + uuid + ext;
        String fileDest= fileProperties.getUriPath() + date +  "/" + uuid + ext;
        File dest = new File(filePath);
        try {
            filePath = dest.getCanonicalPath();
            dest = new File(filePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        //判断文件父目录是否存在
        if (!dest.getParentFile().getParentFile().exists()) {
            boolean result =  dest.getParentFile().getParentFile().mkdir();
            logger.info("文件夹创建结果" + result);
        }
        if (!dest.getParentFile().exists()) {
            boolean result = dest.getParentFile().mkdir();
            logger.info("文件夹创建结果" + result);
        }

        try {
            //保存文件
            file.transferTo(dest);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BusinessException("文件上传失败");
        }
        return fileDest;
    }
}
