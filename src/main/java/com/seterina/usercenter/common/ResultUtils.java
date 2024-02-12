package com.seterina.usercenter.common;

/**
 * 返回工具类
 * @Author：spc
 * @Date：2024/2/8 20:57
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0,data,"ok");
    }
    public static  BaseResponse error(int code,String message,String description) {
        return new BaseResponse<>(code,null,message,description);
    }
}
