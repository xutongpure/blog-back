package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.Tag;
import com.blog.service.TagService;
import com.blog.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author xuton
* @description 针对表【sg_tag(标签)】的数据库操作Service实现
* @createDate 2023-07-21 21:32:52
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




