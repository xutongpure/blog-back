package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.blog.domain.User;
import com.blog.domain.UserRole;
import com.blog.domain.dto.AddUserDto;
import com.blog.domain.dto.UpdateUserDto;
import com.blog.domain.vo.*;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import com.blog.service.RoleService;
import com.blog.service.UserRoleService;
import com.blog.service.UserService;
import com.blog.mapper.UserMapper;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author xuton
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-07-07 14:14:31
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
//        if(!StringUtils.hasText(user.getUserName())){
//            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
//        }
//        if(!StringUtils.hasText(user.getPassword())){
//            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
//        }
//        if(!StringUtils.hasText(user.getEmail())){
//            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
//        }
//        if(!StringUtils.hasText(user.getNickName())){
//            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
//        }
//        //对数据进行是否存在的判断
//        if(userNameExist(user.getUserName())){
//            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
//        }
//        if(nickNameExist(user.getNickName())){
//            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
//        }
        preRegisterCheck(user);
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper) > 0;
    }

    private boolean PhoneNumberExist(String phoneNumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phoneNumber);
        return count(queryWrapper) > 0;
    }

    private boolean EmailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper) > 0;
    }
    @Override
    public ResponseResult pageUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(StringUtils.hasText(userName),User::getUserName,userName);
        userLambdaQueryWrapper.like(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber);
        userLambdaQueryWrapper.like(StringUtils.hasText(status),User::getStatus,status);
        Page<User> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page(page,userLambdaQueryWrapper);
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserVo.class);
        return ResponseResult.okResult(new PageVo(userVos,page.getTotal()));
    }

    public void preRegisterCheck(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
    }

    public void preAddUserCheck(User user) {
        //对数据进行非空判断
        //用户名不能为空
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }

        //对数据进行是否存在的判断
        //用户名必须之前未存在
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //手机号必须之前未存在
        if(PhoneNumberExist(user.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        //邮箱必须之前未存在
        if(EmailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
    }


    @Override
    public ResponseResult addUser(AddUserDto addUserDto) {
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        preAddUserCheck(user);
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        save(user);
        List<UserRole> userRoles = addUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delUser(Long id) {
        removeById(id);
//        getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUser(Long id) {
        //获取用户所关联的角色id列表
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId,id);
        List<Long> userRoleList = userRoleService.list(userRoleLambdaQueryWrapper)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        AdminGetUserVo adminGetUserVo = new AdminGetUserVo();
        adminGetUserVo.setRoleIds(userRoleList);
        //获取所有角色的列表
        List<Role> roleList = roleService.list();
        List<UpdateAllRoleVo> updateAllRoleVos = BeanCopyUtils.copyBeanList(roleList, UpdateAllRoleVo.class);
        adminGetUserVo.setRoles(updateAllRoleVos);
        //获取用户信息
        User user = getById(id);
        UpdateUserVo updateUserVo = BeanCopyUtils.copyBean(user, UpdateUserVo.class);
        adminGetUserVo.setUser(updateUserVo);
        return ResponseResult.okResult(adminGetUserVo);
    }

    @Override
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        updateById(user);
        getBaseMapper().deleteBatch(user.getId());
        List<UserRole> userRoles = updateUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }


}




