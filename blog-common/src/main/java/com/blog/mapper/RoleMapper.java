package com.blog.mapper;

import com.blog.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author xuton
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-07-22 15:02:55
* @Entity com.blog.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
    boolean insertBatch(Long roleId,List<Long> menuIds);

    boolean deleteBatch(Long roleId);
}




