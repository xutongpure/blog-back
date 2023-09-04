package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.domain.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author xuton
* @description 针对表【sg_article(文章表)】的数据库操作Mapper
* @createDate 2023-04-22 15:04:12
* @Entity com.blog.domain.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    boolean updateBatch(List<Article> articles);
}




