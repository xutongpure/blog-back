package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.Link;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddLinkDto;
import com.blog.domain.dto.UpdateLinkDto;

/**
* @author xuton
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-06-05 20:21:45
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult pageLinkList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult getLink(Long id);

    ResponseResult updateLink(UpdateLinkDto updateLinkDto);

    ResponseResult delLink(Long id);
}
