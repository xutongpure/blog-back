package com.blog.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签关联表
 * @TableName sg_article_tag
 */
@TableName(value ="sg_article_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag implements Serializable {
    /**
     * 文章id
     */

    @MppMultiId
    private Long articleId;

    /**
     * 标签id
     */

    @MppMultiId
    private Long tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}