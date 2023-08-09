package com.blog.mapper;

import com.blog.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author xuton
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-07-22 15:02:55
* @Entity com.blog.domain.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);
    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Menu> selectAllMenu();

    List<Menu> selectRoleMenuByUserId(Long roleId);

    List<Long> selectByRoleId(Long id);
}




