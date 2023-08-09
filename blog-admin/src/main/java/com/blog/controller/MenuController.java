package com.blog.controller;

import com.blog.domain.Menu;
import com.blog.domain.ResponseResult;
import com.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system/menu")
public class MenuController {

    @Autowired
    MenuService menuService;
    @GetMapping("/list")
    public ResponseResult list(String status, String menuName) {
        return menuService.menuList(status,menuName);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable("id") Long id) {
        return menuService.getMenu(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult delMenu(@PathVariable("menuId") Long menuId) {
        return menuService.delMenu(menuId);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeSelect() {
        return menuService.treeSelect();
    }
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable("id") Long id) {
        return menuService.roleMenuTreeselect(id);
    }
}
