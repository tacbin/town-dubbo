package com.tacbin.town.web.threads.files;

import java.io.InputStream;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-10 22:30
 **/
public interface IFileUploadToOtherService {
    /**
     * 上传1
     *
     * @param localFilePath
     * @param fileName
     */
    void upload(String localFilePath, String fileName);

    /**
     * 上传
     *
     * @param inputStream
     * @param fileName
     */
    void upload2(InputStream inputStream, String fileName);
}
