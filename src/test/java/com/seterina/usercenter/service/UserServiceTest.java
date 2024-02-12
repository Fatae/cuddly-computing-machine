//package com.seterina.usercenter.service;
//
//import com.seterina.usercenter.model.domain.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//
//
///**
// * @Author：spc
// * @Date：2024/2/5 21:39
// */
//@SpringBootTest
//class UserServiceTest {
//    @Resource
//    UserService userService;
//
//    @Test
//    void testAdd() {
//
//        User user = new User();
//        user.setUsername("seterina");
//        user.setUserAccount("123");
//        user.setAvatarUal("http://acm.zzuli.edu.cn/image/logo.png");
//        user.setUserPassword("123456");
//        user.setPhone("123456");
//        user.setEmail("2315");
//        boolean result = userService.save(user);
//        System.out.println(user.getId());
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void userRegister() {
//        String userAccount = "yupi";
//        String userPassword = "";
//        String checkPassword = "123456";
//        String planetCode = "1";
//        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1, result);
//        userAccount = "yu";
//        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1, result);
//        userAccount = "yupi";
//        userPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1, result);
//        userAccount = "yu pi";
//        userPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1, result);
//        checkPassword = "123456789";
//        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1, result);
//        userAccount = "seterina";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1, result);
//        userAccount = "yupi";
//        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertTrue(result > 0);
//    }
//}