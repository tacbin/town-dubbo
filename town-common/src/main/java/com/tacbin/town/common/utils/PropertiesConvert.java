package com.tacbin.town.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-06-06 12:46
 **/
@Slf4j
public class PropertiesConvert {
    public static <T, R> void copyObjectRepoToApi(T source, R dest) {
        if (source == null) {
            return;
        }
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("转换失败:{}", e.getMessage());
        }
    }

    public static <T, R> void copyListObjectOfRepoToApi(List<T> source, List<R> dest) {
        if (dest.size() != source.size()) {
            log.error("对象转换异常,list对象数不相等!");
            return;
        } else {
            try {
                for (int i = 0; i < source.size(); i++) {
                    BeanUtils.copyProperties(dest.get(i), source.get(i));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("转换失败:{}", e.getMessage());
            }
        }

    }

}
