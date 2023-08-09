package com.blog.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleStatusDto {

    /**
     * 角色ID
     */
    @JSONField(alternateNames = "roleId")
    private Long id;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
}
