package com.seterina.usercenter.service;

import com.seterina.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author fate
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-02-05 21:16:40
*/
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 第二次密码
     * @return 用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 对用户进行脱敏
     * @param user 脱敏前的用户信息
     * @return 脱敏后的用户信息
     */
    User getSateyUser(User user);

    /**
     * 查询用户
     * @param username 用户名
     * @param httpServletRequest 获取用户的登录态
     * @return 查询的列表
     */
    List<User> selectUser(String username,HttpServletRequest httpServletRequest);

    /**
     * 删除用户
     * @param id 用户id
     * @param httpServletRequest 获取用户的登录态
     * @return 是否删除成功
     */
    boolean deleteUser(int id,HttpServletRequest httpServletRequest);

    boolean isAdmin(HttpServletRequest request);

    /**
     *  用户注销
     * @param httpServletRequest 移除用户的登录态
     */
    int userLogout(HttpServletRequest httpServletRequest);
}
