package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAllTagVo {
    private Long id;

    /**
     * 标签名
     */
    private String name;
}
