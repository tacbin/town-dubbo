package com.tacbin.town.web.threads.files;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-10 22:31
 **/
@Slf4j
public class UploadToQiNiu implements IFileUploadToOtherService {
    private final static String accessKey = "q4h_EU7JF2emdNzmvb2HmxA6BgcEg8ngA3oehRbz";

    private final static String secretKey = "x9ppRsCcmIKdmGfkNuj63nGy7fOV6MfCG4oloW8S";

    private final static String bucket = "tacbin-demo";

    @Override
    public void upload(String localFilePath, String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            uploadManager.put(localFilePath, key, upToken);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
