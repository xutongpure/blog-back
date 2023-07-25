package com.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xuton
* @description 针对表【sg_comment(评论表)】的数据库操作Mapper
* @createDate 2023-07-10 11:28:22
* @Entity generator.domain.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




