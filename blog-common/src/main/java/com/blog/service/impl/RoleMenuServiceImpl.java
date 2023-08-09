package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.RoleMenu;
import com.blog.service.RoleMenuService;
import com.blog.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author xuton
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2023-08-08 22:12:23
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




