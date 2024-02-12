package com.seterina.usercenter.model.domain.Request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @Author：spc
 * @Date：2024/2/6 17:30
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 7264934529315172179L;
    String userAccount;
    String userPassword;
}
