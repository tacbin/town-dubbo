package com.tacbin.weixin.web.controller.message;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMassMessageService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 19:31
 **/
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/mass-message")
public class MassMessageController {
    private WxMpService mpService;

    @RequestMapping(path = "/upload-news", method = RequestMethod.POST)
    public ResponseInfo<String> uploadNews(@RequestBody WxMpMassNews news) {
        WxMpMassMessageService massMessageService = mpService.getMassMessageService();
        try {
            massMessageService.massNewsUpload(news);
            return new ResponseInfo<>("success", Status.SUCCESS, null);
        } catch (WxErrorException e) {
            log.error("upload failed:{}", e.getMessage());
            return new ResponseInfo<>("上传失败", Status.FAIL, e.getMessage());
        }
    }
}
