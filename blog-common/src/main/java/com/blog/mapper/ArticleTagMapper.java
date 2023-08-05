package com.blog.mapper;

import com.blog.domain.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xuton
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Mapper
* @createDate 2023-08-05 15:26:15
* @Entity generator.domain.SgArticleTag
*/
@Mapper
public interface ArticleTagMapper extends MppBaseMapper<ArticleTag> {

}




