package com.hailian.ylwmall.controller.vo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by 19012964 on 2020/4/29.
 */
public class FileResult implements Serializable {
    private static final long serialVersionUID = -6875866018506083219L;
    private boolean success;
    private String msg;
    private String fileUUID;
    private byte[] fileBytes;
    private String fileName;
    private String fileContent;
    private String contentType;

    public FileResult() {
    }

    public InputStream getInputStream() {
        return this.fileBytes != null && this.fileBytes.length != 0?new ByteArrayInputStream(this.fileBytes):null;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFileUUID() {
        return this.fileUUID;
    }

    public void setFileUUID(String fileUUID) {
        this.fileUUID = fileUUID;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public byte[] getFileBytes() {
        return this.fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return this.fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
