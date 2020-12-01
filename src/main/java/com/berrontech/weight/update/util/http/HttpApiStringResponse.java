package com.berrontech.weight.update.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by 郭文梁 2019/9/9 14:39
 * HttpApiStringResponse
 * HttpApiStringResponse
 *
 * @author 郭文梁
 * @data 2019/9/9 14:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpApiStringResponse {
    private int statusCode;
    private String responseBody;

    /**
     * 转换为JAVA对象
     *
     * @param klass klass
     * @param <T>   类型对象
     * @return java object
     */
    public <T> T asObject(Class<T> klass) {
        return JSON.parseObject(responseBody, klass);
    }

    /**
     * 转换为 JSONObject 对象
     *
     * @return JSONObject
     */
    public JSONObject asJsonObject() {
        return JSON.parseObject(responseBody);
    }
}
