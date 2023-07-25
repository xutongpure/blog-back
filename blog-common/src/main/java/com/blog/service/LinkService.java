package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.Link;
import com.blog.domain.ResponseResult;

/**
* @author xuton
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-06-05 20:21:45
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
