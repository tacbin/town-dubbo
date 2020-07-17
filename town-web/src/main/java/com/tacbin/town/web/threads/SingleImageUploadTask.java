package com.tacbin.town.web.threads;

import com.tacbin.town.common.constants.AppConstants;
import com.tacbin.town.common.utils.SnowFlakeUtil;
import com.tacbin.town.web.threads.files.IFileUploadToOtherService;
import com.tacbin.town.web.threads.files.UploadToQiNiu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-08 22:07
 **/
@Slf4j
public class SingleImageUploadTask implements Runnable {
    private IFileUploadToOtherService fileUploadToOtherService;
    private MultipartFile image;
    private String imgId;
    private String imgPath;

    public SingleImageUploadTask(MultipartFile image) {
        this.image = image;
        imgId = SnowFlakeUtil.generateId() + image.getOriginalFilename();
        imgPath = "images/" + imgId;
        fileUploadToOtherService = new UploadToQiNiu();
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        log.info("文件开始上传");
        try {
            String path = uploadSingleImageToServer();
            log.info("文件上传完成,耗时:{}s", System.currentTimeMillis() / start / 1000);
            log.info("文件开始上传到其他存储服务");
            start = System.currentTimeMillis();
            fileUploadToOtherService.upload(path, imgPath);
            log.info("文件开始上传到其他存储服务完成,耗时:{}s", System.currentTimeMillis() / start / 1000);
        } catch (Exception e) {
            log.error("文件上传出错，错误信息:{}", e.getMessage());
        }
    }

    /**
     * 上传单个图片到服务器
     *
     * @return 图片相对路径
     * @throws IOException
     */
    private String uploadSingleImageToServer() throws Exception {
        if (image == null || image.isEmpty()) {
            return null;
        }
        String filePath = "user.home";
        String imgSrc;
        // 获取根目录；linux->root ; windows->C:\Users\Administrator
        String userHome = System.getProperty(filePath);
        File folder = new File(userHome + "/app/images/");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        byte[] bytes = image.getBytes();
        // 生成路径，上传至具体的目录下例如，C:\Users\Administrator\app\images\
        imgSrc = userHome + "/app/images/" + imgId;
        Path path = Paths.get(imgSrc);
        Files.write(path, bytes);
        return imgSrc;
    }

    public String getImgId() {
        return AppConstants.IMAGE_HOST + imgPath;
    }
}
