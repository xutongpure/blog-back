package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.domain.Link;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xuton
* @description 针对表【sg_link(友链)】的数据库操作Mapper
* @createDate 2023-06-05 20:21:45
* @Entity com.blog.domain.Link
*/
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}




