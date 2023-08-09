package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.ArticleListDto;
import com.blog.domain.dto.RoleDto;
import com.blog.domain.dto.RoleStatusDto;
import com.blog.domain.dto.UpdateRoleDto;
import com.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String roleName, String status) { //用Dto是为了可拓展
        return roleService.pageRoleList(pageNum,pageSize,roleName,status);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto) {
        return roleService.changeStatus(roleStatusDto);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody RoleDto roleDto) {
        return roleService.addRole(roleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable("id") Long id) {
        return roleService.getRole(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto) {
        return roleService.updateRole(updateRoleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delRole(@PathVariable("id") Long id) {
        return roleService.delRole(id);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole() {
        return roleService.listAllRole();
    }
}
