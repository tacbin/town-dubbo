package com.tacbin.town.web.controller;

import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.threads.SingleImageUploadTask;
import com.tacbin.town.web.threads.TownThreadFactory;
import com.tacbin.town.web.util.ImageValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description :文件处理
 * @Author : Administrator
 * @Date : 2020-06-08 21:45
 **/
@RestController
@RequestMapping("/files")
@Slf4j
public class FilesController {
    @RequestMapping(path = "/oneImageUpload", method = RequestMethod.POST)
    @AnalysisLog
    public ResponseInfo<String> uploadSingleFile(MultipartFile file) throws Exception {
        log.info("single文件大小{}k", file.getSize() / 1024);
        ImageValidationUtil.imageValidate(file);
        SingleImageUploadTask task = new SingleImageUploadTask(file);
        TownThreadFactory.execute(task);
        String url = task.getImgId();
        // 礼貌性暂停一秒
        Thread.sleep(1000);
        return new ResponseInfo<>(null, Status.SUCCESS, url);
    }


    @RequestMapping(path = "/threeImagesUpload", method = RequestMethod.POST)
    @AnalysisLog
    public ResponseInfo<String[]> uploadThreeFile(MultipartFile file0, MultipartFile file1, MultipartFile file2) throws Exception {
        long file0Size = file0 == null ? 0 : file0.getSize();
        long file1Size = file1 == null ? 0 : file1.getSize();
        long file2Size = file2 == null ? 0 : file2.getSize();
        log.info("three文件大小{}k", (file0Size + file1Size + file2Size) / 1024);
        String[] urls = new String[3];
        MultipartFile[] files = {file0, file1, file2};
        for (int i = 0; i < files.length; i++) {
            if (files[i] == null) {
                continue;
            }
            ImageValidationUtil.imageValidate(files[i]);
            SingleImageUploadTask task = new SingleImageUploadTask(files[i]);
            TownThreadFactory.execute(task);
            urls[i] = task.getImgId();
        }
        // 礼貌性暂停一秒
        Thread.sleep(1000);
        return new ResponseInfo<>(null, Status.SUCCESS, urls);
    }
}
