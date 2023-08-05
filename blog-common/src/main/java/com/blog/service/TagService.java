package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.AddTagDto;
import com.blog.domain.dto.TagListDto;
import com.blog.domain.vo.PageVo;

/**
* @author xuton
* @description 针对表【sg_tag(标签)】的数据库操作Service
* @createDate 2023-07-21 21:32:52
*/
public interface TagService extends IService<Tag> {

    ResponseResult deleteTag(Long id);

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult getTag(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult listAllTag();

}
