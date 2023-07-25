package com.blog.mapper;

import com.blog.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xuton
* @description 针对表【sg_category(分类表)】的数据库操作Mapper
* @createDate 2023-05-09 21:17:22
* @Entity com.blog.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




