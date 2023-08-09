package com.blog.domain.vo;

import com.blog.domain.Role;
import com.blog.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("根据id查询用户信息回显Vo")
public class AdminGetUserVo {
    //用户所关联的角色id列表
    private List<Long> roleIds;
    //所有角色的列表
    private List<UpdateAllRoleVo> roles;
    //用户信息
    private UpdateUserVo user;
}
