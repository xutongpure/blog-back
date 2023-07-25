package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.Article;
import com.blog.domain.ResponseResult;

/**
* @author xuton
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2023-04-22 15:04:12
*/
@SuppressWarnings("all")
public interface ArticleService extends IService<Article> {

    ResponseResult<Article> hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

}
