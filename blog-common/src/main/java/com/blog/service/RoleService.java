package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.RoleDto;
import com.blog.domain.dto.RoleStatusDto;
import com.blog.domain.dto.UpdateRoleDto;

import java.util.List;

/**
* @author xuton
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-07-22 15:02:55
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult pageRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleStatusDto roleStatusDto);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult getRole(Long id);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult delRole(Long id);

    ResponseResult listAllRole();
}
