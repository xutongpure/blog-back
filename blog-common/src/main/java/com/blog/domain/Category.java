package com.blog.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类表
 * @TableName sg_category
 */
@TableName(value ="sg_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 父分类id，如果没有父分类为-1
     */
    private Long pid;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态0:正常,1禁用
     */
    private String status;

    /**
     * 
     */
    private Long createBy;

    /**
     * 
     */
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 
     */
    private Long updateBy;

    /**
     * 
     */
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}