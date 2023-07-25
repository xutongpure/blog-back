package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xuton
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-07-07 14:14:31
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
