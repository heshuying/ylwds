package ltd.newbee.mall.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.haier.openplatform.hfs.client.dto.FileRequest;
import com.haier.openplatform.hfs.client.dto.FileResult;
import com.haier.openplatform.hfs.client.service.FileServiceClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * @author zhp.dts
 * @date 2019/2/14.
 */
@Service
public class HfsClientService {

    private final static String HFS_APP_NAME="ylwds";

    @Reference(check=false,version = "1.0.0",timeout = 120000,interfaceClass = FileServiceClient.class,owner="hop")
    private FileServiceClient fileServiceClient;


    public FileResult saveFile(String fileName,byte[] fileBytes){
        FileRequest request = create();
        request.setFileName(fileName);
        request.setBytes(fileBytes);
        return fileServiceClient.saveFile(request);
    }
    public FileResult findFile(String fileId){
        FileRequest request = create();
        request.setFileId(fileId);
        return fileServiceClient.findFileByUUID(request);
    }
    public FileResult deleteFile(String fileId){
        FileRequest request = create();
        request.setFileId(fileId);
        return fileServiceClient.deleteFileByUID(request);
    }

    /**
     * 创建基本的请求类
     * @return
     */
    private FileRequest create(){
        FileRequest request = new FileRequest();
        request.setAppName(HFS_APP_NAME);
        return request;
    }
}
