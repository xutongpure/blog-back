package com.blog.service;

import com.blog.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.vo.MenuVo;

import java.util.List;

/**
* @author xuton
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-07-22 15:02:55
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

}
