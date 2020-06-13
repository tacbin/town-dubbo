package com.tacbin.town.web.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-08 23:40
 **/
public class ImageValidationUtil {
    /**
     * 文件类型校验
     *
     * @param image
     * @throws Exception
     */
    public static void imageValidate(MultipartFile image) throws Exception {
        String content = image.getContentType().toLowerCase();
        String[] types = new String[]{"image/bmp", "image/jpg", "image/jpeg", "image/png", "image/gif"};
        List<String> imageTypes = Arrays.asList(types);
        if (!imageTypes.contains(content)) {
            throw new Exception("请上传图片");
        }
    }

    public static void imagesValidate(MultipartFile[] images) throws Exception {
        for (MultipartFile image : images) {
            imageValidate(image);
        }
    }
}
