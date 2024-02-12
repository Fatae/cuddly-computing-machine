package com.seterina.usercenter.model.domain.Request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author：spc
 * @Date：2024/2/6 17:30
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 7055588564400355504L;

    String userAccount;
    String userPassword;
    String checkPassword;
    String planetCode;
}
