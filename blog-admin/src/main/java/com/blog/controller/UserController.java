package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddUserDto;
import com.blog.domain.dto.UpdateUserDto;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        return userService.pageUserList(pageNum,pageSize,userName,phonenumber,status);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult delUser(@PathVariable("id") Long id) {
        return userService.delUser(id);
    }

    @GetMapping("{id}")
    public ResponseResult getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }
}
