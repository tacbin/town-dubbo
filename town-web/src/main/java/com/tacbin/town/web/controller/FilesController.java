package com.tacbin.town.web.controller;

import com.tacbin.town.common.entity.ResponseInfo;
import com.tacbin.town.common.entity.Status;
import com.tacbin.town.common.utils.SnowFlakeUtil;
import com.tacbin.town.web.aop.AnalysisLog;
import com.tacbin.town.web.threads.SingleImageUploadTask;
import com.tacbin.town.web.threads.TownThreadFactory;
import com.tacbin.town.web.threads.files.IFileUploadToOtherService;
import com.tacbin.town.web.threads.files.UploadToQiNiu;
import com.tacbin.town.web.util.ImageValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description :文件处理
 * @Author : Administrator
 * @Date : 2020-06-08 21:45
 **/
@RestController
@RequestMapping("/files")
@Slf4j
public class FilesController {
    private static final IFileUploadToOtherService uploadService = new UploadToQiNiu();

    @RequestMapping(path = "/oneImageUpload", method = RequestMethod.POST)
    @AnalysisLog
    public ResponseInfo<String> uploadSingleFile(MultipartFile file) throws Exception {
        log.info("single文件大小{}k", file.getSize() / 1024);
        String fileName = SnowFlakeUtil.generateId() + file.getOriginalFilename();
        uploadService.upload2(file.getInputStream(), fileName);
        return new ResponseInfo<>(null, Status.SUCCESS, "http://images.tacbin.club/" + fileName);
    }

    @RequestMapping(path = "/twoImagesUpload", method = RequestMethod.POST)
    @AnalysisLog
    public ResponseInfo<String[]> uploadThreeFile(MultipartFile file0, MultipartFile file1) throws Exception {
        List<MultipartFile> fileList = Arrays.asList(file0, file1);
        String[] urls = new String[2];
        int i = 0;
        for (MultipartFile file : fileList) {
            if (file == null) {
                i++;
                continue;
            }
            String fileName = SnowFlakeUtil.generateId() + file.getOriginalFilename();
            uploadService.upload2(file.getInputStream(), fileName);
            urls[i] = "http://images.tacbin.club/" + fileName;
            i++;
        }
        return new ResponseInfo<>(null, Status.SUCCESS, urls);
    }

    @RequestMapping(path = "/threeImagesUpload", method = RequestMethod.POST)
    @AnalysisLog
    public ResponseInfo<String[]> uploadThreeFile(MultipartFile file0, MultipartFile file1, MultipartFile file2) throws Exception {
        long file0Size = file0 == null ? 0 : file0.getSize();
        long file1Size = file1 == null ? 0 : file1.getSize();
        long file2Size = file2 == null ? 0 : file2.getSize();
        log.info("three文件大小{}k", (file0Size + file1Size + file2Size) / 1024);
        List<MultipartFile> fileList = Arrays.asList(file0, file1);
        String[] urls = new String[3];
        int i = 0;
        for (MultipartFile file : fileList) {
            if (file == null) {
                i++;
                continue;
            }
            String fileName = SnowFlakeUtil.generateId() + file.getOriginalFilename();
            uploadService.upload2(file.getInputStream(), fileName);
            urls[i] = "http://images.tacbin.club/" + fileName;
            i++;
        }
        return new ResponseInfo<>(null, Status.SUCCESS, urls);
    }
}
