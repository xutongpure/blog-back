package com.blog.mapper;

import com.blog.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xuton
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-07-07 14:14:31
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




