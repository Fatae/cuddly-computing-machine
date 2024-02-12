package com.seterina.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

        /**
         * 用户表
         * @TableName user
         */
        @TableName(value ="user")
        @Data
        public  class User implements Serializable {
            /**
             * 用户id，主键
             */
            @TableId(type = IdType.AUTO)
            private Long id;

            /**
             * 昵称
             */
            private String username;

            /**
             * 账号
             */
            private String userAccount;

            /**
             * 用户头像
             */
            private String avatarUal;

            /**
             * 性别
             */
            private Integer gender;

            /**
             * 密码
             */
            private String userPassword;

            /**
             * 电话
             */
            private String phone;

            /**
             * 邮箱
             */
            private String email;

            /**
             * 用户状态，0为正常
             */
            private Integer userStatus;

            /**
             * 创建时间
             */
            private Date createTime;

            /**
             * 数据更新时间
             */
            private Date updateTime;

            /**
             * 是否删除
             */
            @TableLogic
            private Integer isDelete;

            /**
             * 用户角色
             */
            private Integer userRole;

            /**
             * 星球编号
             */
            private String planetCode;

            @TableField(exist = false)
            private static final long serialVersionUID = 1L;
        }
