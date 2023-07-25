package com.blog.service;

import com.blog.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author xuton
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-07-22 15:02:55
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

}
