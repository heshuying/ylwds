package com.hailian.ylwmall.service.impl;


import com.hailian.ylwmall.config.FileProperties;
import com.hailian.ylwmall.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@EnableConfigurationProperties({
        FileProperties.class
})
@Service
public class FileService {
    private final Path fileStorageLocation;
    private final String staticPath;

    @Autowired
    public FileService(FileProperties fileProperties) {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        this.staticPath=fileProperties.getUriPath();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new BusinessException("Could not create the directory where the uploaded files will be stored.");
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new BusinessException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new BusinessException("Could not store file " + fileName + ". Please try again!");
        }
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            if(fileName.contains(this.staticPath)){
                fileName=fileName.replace(this.staticPath,"");
            }
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new BusinessException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new BusinessException("File not found " + fileName);
        }
    }
}
