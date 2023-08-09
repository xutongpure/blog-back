package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.Menu;
import com.blog.domain.ResponseResult;
import com.blog.domain.vo.*;
import com.blog.service.MenuService;
import com.blog.mapper.MenuMapper;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author xuton
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-07-22 15:02:55
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        //如果用户id为1代表超级管理员，menus中需要有所有菜单类型为C或者M的，状态为正常的，未被删除的权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<MenuVo> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult menuList( String status,String menuName) {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        menuLambdaQueryWrapper.like(StringUtils.hasText(status),Menu::getStatus,status);
        menuLambdaQueryWrapper.orderByAsc(Menu::getOrderNum);
        menuLambdaQueryWrapper.orderByAsc(Menu::getParentId);
        List<Menu> menuList = list(menuLambdaQueryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menuList, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);

    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenu(Long id) {
        Menu menu = getById(id);
        UpdateMenuVo updateMenuVo = BeanCopyUtils.copyBean(menu, UpdateMenuVo.class);
        return ResponseResult.okResult(updateMenuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if(menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单" + menu.getMenuName() + "失败，上级菜单不能选择为自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delMenu(Long menuId) {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getParentId,menuId);
        List<Menu> menuList = list(menuLambdaQueryWrapper);
        if(ObjectUtils.isNull(menuList)) {
            removeById(menuId);
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(500,"存在子菜单,删除失败");
    }


    private List<MenuVo> builderMenuTree(List<Menu> menus, Long parentId) {
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        List<MenuVo> menuTree = menuVos.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(getChildren(menu,menuVos)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menu, List<MenuVo> menus) {
        List<MenuVo> childrenList = menus.stream()
                .filter((MenuVo m) -> {
                    return m.getParentId().equals(menu.getId());
                })
                .peek(m -> m.setChildren(getChildren(m, menus)))  //peek用于修改数据，map用于转换数据类型
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public ResponseResult treeSelect() {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = menuMapper.selectAllMenu();
        List<MenuTreeSelectVo> menuTree = builderMenuSelectTree(menus,0L);
        return ResponseResult.okResult(menuTree);

    }



    private List<MenuTreeSelectVo> builderMenuSelectTree(List<Menu> menus, Long parentId) {
        List<MenuTreeSelectVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuTreeSelectVo.class);
        List<MenuTreeSelectVo> menuTree = menuVos.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(getMenuChildren(menu,menuVos)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<MenuTreeSelectVo> getMenuChildren(MenuTreeSelectVo menu, List<MenuTreeSelectVo> menus) {
        List<MenuTreeSelectVo> childrenList = menus.stream()
                .filter((MenuTreeSelectVo m) -> {
                    return m.getParentId().equals(menu.getId());
                })
                .peek(m -> m.setChildren(getMenuChildren(m, menus)))  //peek用于修改数据，map用于转换数据类型
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = menuMapper.selectAllMenu();
        List<Long> checkedKeys = menuMapper.selectByRoleId(id);
        List<MenuTreeSelectVo> menuTree = builderMenuSelectTree(menus,0L);
        RoleMenuTreeSelectVo roleMenuTreeSelectVo = new RoleMenuTreeSelectVo(menuTree, checkedKeys);
        return ResponseResult.okResult(roleMenuTreeSelectVo);
    }


}




