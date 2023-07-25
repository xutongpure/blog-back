package com.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.Comment;
import com.blog.domain.ResponseResult;

/**
* @author xuton
* @description 针对表【sg_comment(评论表)】的数据库操作Service
* @createDate 2023-07-10 11:28:22
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
