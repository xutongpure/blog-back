package com.blog.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeSelectVo {
    private Long id;

    /**
     * 菜单名称
     */
    @JSONField(name = "label")
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;


    /**
     * 导航栏子标签
     */
    private List<MenuTreeSelectVo> children;
}
