package com.seterina.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seterina.usercenter.common.ErrorCode;
import com.seterina.usercenter.exception.BusinessException;
import com.seterina.usercenter.model.domain.User;
import com.seterina.usercenter.service.UserService;
import com.seterina.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.seterina.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.seterina.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author fate
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-02-05 21:16:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Resource
    UserMapper userMapper;

    final String SALT = "seterina";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        //校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"有内容为空");
        }
        if (!(userPassword.equals(checkPassword))){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码不一致");
        }
        if (userAccount.length() < 4 || userPassword.length() <8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码过长");
        }
        if (planetCode.length() >5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
        }
        //校验是否包含特殊字符
        String regEx =  ".*[\\s`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(userAccount);
        boolean matches = m.matches();
        if (matches){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号含有特殊字符");
        }
        //账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //创建一个 QueryWrapper 对象，用于构建查询条件。
        //使用 eq() 方法添加一个等于条件，要求查询结果中的 userAccount 字段的值必须等于传入的 userAccount 变量的值。
        userQueryWrapper.eq("userAccount",userAccount);
        long res = userMapper.selectCount(userQueryWrapper);
        if (res > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }
        userQueryWrapper = new QueryWrapper<>();
        //创建一个 QueryWrapper 对象，用于构建查询条件。
        //使用 eq() 方法添加一个等于条件，要求查询结果中的 userAccount 字段的值必须等于传入的 userAccount 变量的值。
        userQueryWrapper.eq("planetCode",planetCode);
         res = userMapper.selectCount(userQueryWrapper);
        if (res > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号重复");
        }
        //对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        int insert = userMapper.insert(user);
        if (insert !=1){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统内部异常");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //校验
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }
        if (userAccount.length() < 4 || userPassword.length() <8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号小于四位或密码小于八位");
        }
        //校验是否包含特殊字符
        String regEx =  ".*[\\s`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(userAccount);
        boolean matches = m.matches();
        if (matches){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号包含特殊字符");
        }
        //对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //校验账号密码是否正确
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount",userAccount);
        userQueryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不正确");
        }
        //用户脱敏
        User sateyUser = getSateyUser(user);
        //记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,sateyUser);
        return sateyUser;
    }

    //用户信息脱敏
    @Override
    public User getSateyUser(User user) {
        if (user == null){
            return null;
        }
        User sateyUser = new User();
        sateyUser.setId(user.getId());
        sateyUser.setUsername(user.getUsername());
        sateyUser.setUserAccount(user.getUserAccount());
        sateyUser.setAvatarUal(user.getAvatarUal());
        sateyUser.setGender(user.getGender());
        sateyUser.setPhone(user.getPhone());
        sateyUser.setEmail(user.getEmail());
        sateyUser.setPlanetCode(user.getPlanetCode());
        sateyUser.setUserStatus(user.getUserStatus());
        sateyUser.setCreateTime(user.getCreateTime());
        sateyUser.setIsDelete(user.getIsDelete());
        sateyUser.setUserRole(user.getUserRole());
        return sateyUser;
    }

    @Override
    public List<User> selectUser(String username,HttpServletRequest httpServletRequest) {
        if (!isAdmin(httpServletRequest)){
            throw new BusinessException(ErrorCode.NO_AUTO,"没有权限");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            userQueryWrapper.like("username",username);
        }

        List<User> users = userMapper.selectList(userQueryWrapper);
        return users.stream().map(this::getSateyUser).collect(Collectors.toList());
    }

    @Override
    public boolean deleteUser(int id, HttpServletRequest httpServletRequest) {
        if (!isAdmin(httpServletRequest)){
            throw new BusinessException(ErrorCode.NO_AUTO,"没有权限");
        }
        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"没有该用户");
        }
        return this.removeById(id);
    }

    @Override
    public boolean isAdmin(HttpServletRequest httpServletRequest) {
        Object attribute = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) attribute;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

    @Override
    public int userLogout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

}




