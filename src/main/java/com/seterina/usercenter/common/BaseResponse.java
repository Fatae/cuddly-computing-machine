package com.seterina.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/** 通用返回类
 * @Author：spc
 * @Date：2024/2/8 20:21
 */
@Data
public class BaseResponse<T> implements Serializable {

      private   int code;
      private   String message;
      private String description;
      private   T data;

    public BaseResponse(int code,  T data,String message,String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.message = "";
        this.data = data;
        this.description = "";
    }

    public BaseResponse(int code, T data,String message) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = "";
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }
}
