package com.seterina.usercenter.controller;

/*
  @Author：spc
 * @Date：2024/2/6 17:03
 */


import com.seterina.usercenter.common.BaseResponse;
import com.seterina.usercenter.common.ErrorCode;
import com.seterina.usercenter.common.ResultUtils;
import com.seterina.usercenter.exception.BusinessException;
import com.seterina.usercenter.model.domain.Request.UserLoginRequest;
import com.seterina.usercenter.model.domain.Request.UserRegisterRequest;
import com.seterina.usercenter.model.domain.User;
import com.seterina.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.seterina.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest request){
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = request.getUserAccount();
        String userPassword = request.getUserPassword();
        String checkPassword = request.getCheckPassword();
        String planetCode = request.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"有数据为空");
        }
        long res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(res);
    }
    @PostMapping("login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest request,HttpServletRequest request1){
        if (request ==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"有数据为空");
        }
        String userAccount = request.getUserAccount();
        String userPassword = request.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"有数据为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request1);
        return ResultUtils.success(user);
    }
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest httpServletRequest){
        if (httpServletRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"有数据为空");
        }
        int res = userService.userLogout(httpServletRequest);
        return ResultUtils.success(res);
    }
    @GetMapping("/search")
    public BaseResponse<List<User>> userSearch(String username,HttpServletRequest request){

        List<User> users = userService.selectUser(username, request);
        return ResultUtils.success(users);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(int id,HttpServletRequest request){
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"id不符合要求");
        }
        boolean res = userService.deleteUser(id, request);
        return ResultUtils.success(res);
    }
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest httpServletRequest){
        Object attribute = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) attribute;
        if (currentUser ==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"当前用户为空");
        }
        Long userId = currentUser.getId();
        User byId = userService.getById(userId);
        User sateyUser = userService.getSateyUser(byId);
        return ResultUtils.success(sateyUser);
    }
}
