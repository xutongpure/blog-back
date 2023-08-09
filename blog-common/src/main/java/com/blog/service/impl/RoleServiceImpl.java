package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.blog.domain.dto.RoleDto;
import com.blog.domain.dto.RoleStatusDto;
import com.blog.domain.dto.UpdateRoleDto;
import com.blog.domain.vo.ListAllRoleVo;
import com.blog.domain.vo.PageVo;
import com.blog.domain.vo.UpdateRoleVo;
import com.blog.service.RoleService;
import com.blog.mapper.RoleMapper;
import com.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.blog.constants.SystemConstants.ROLE_STATUS_NORMAL;

/**
* @author xuton
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-07-22 15:02:55
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是超级管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult pageRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.like(StringUtils.hasText(roleName),Role::getRoleName,roleName);
        roleLambdaQueryWrapper.like(StringUtils.hasText(status),Role::getStatus,status);

        Page<Role> rolePage = new Page<>();
        rolePage.setCurrent(pageNum);
        rolePage.setSize(pageSize);
        page(rolePage,roleLambdaQueryWrapper);
        List<Role> roleListVo = BeanCopyUtils.copyBeanList(rolePage.getRecords(), Role.class);
        return ResponseResult.okResult(new PageVo(roleListVo,rolePage.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(RoleStatusDto roleStatusDto) {
        Role role = BeanCopyUtils.copyBean(roleStatusDto, Role.class);
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);
        roleMapper.insertBatch(role.getId(), roleDto.getMenuIds());

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Long id) {
        Role role = getById(id);
        UpdateRoleVo updateRoleVo = BeanCopyUtils.copyBean(role, UpdateRoleVo.class);
        return ResponseResult.okResult(updateRoleVo);
    }

    @Override
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);
        roleMapper.deleteBatch(role.getId());
        roleMapper.insertBatch(role.getId(), updateRoleDto.getMenuIds());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delRole(Long id) {
        removeById(id);
//        roleMapper.deleteBatch(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(Role::getStatus,ROLE_STATUS_NORMAL);
        List<Role> roleList = list(roleLambdaQueryWrapper);
        List<ListAllRoleVo> listAllRoleVos = BeanCopyUtils.copyBeanList(roleList, ListAllRoleVo.class);
        return ResponseResult.okResult(listAllRoleVos);
    }
}




