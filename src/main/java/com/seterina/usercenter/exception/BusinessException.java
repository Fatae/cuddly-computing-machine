package com.seterina.usercenter.exception;

import com.seterina.usercenter.common.ErrorCode;

/**
 * 自定义异常类
 * 给原本的异常处理新增了两个参数，并且可以自定义添加描述
 * @Author：spc
 * @Date：2024/2/8 21:22
 */
public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }
    public BusinessException( ErrorCode errorCode,String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
