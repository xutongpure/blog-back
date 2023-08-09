package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.AddUserDto;
import com.blog.domain.dto.UpdateUserDto;

/**
* @author xuton
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-07-07 14:14:31
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult pageUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult delUser(Long id);

    ResponseResult getUser(Long id);

    ResponseResult updateUser(UpdateUserDto updateUserDto);
}
