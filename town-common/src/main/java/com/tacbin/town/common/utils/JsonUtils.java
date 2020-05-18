package com.tacbin.town.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-05-03 1:57
 **/
public class JsonUtils {
    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(obj);
    }
}
