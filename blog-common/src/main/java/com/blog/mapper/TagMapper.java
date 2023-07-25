package com.blog.mapper;

import com.blog.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xuton
* @description 针对表【sg_tag(标签)】的数据库操作Mapper
* @createDate 2023-07-21 21:32:52
* @Entity generator.domain.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




