package com.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "新增标签Dto")
public class AddTagDto {
    /**
     * 标签名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
