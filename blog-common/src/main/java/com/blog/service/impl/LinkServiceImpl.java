package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.ResponseResult;
import com.blog.service.LinkService;
import com.blog.domain.Link;
import com.blog.mapper.LinkMapper;
import com.blog.utils.BeanCopyUtils;
import com.blog.domain.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author xuton
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2023-06-05 20:21:45
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService {

    @Override
    public ResponseResult getAllLink() {

        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(QueryWrapper);

        //转换成Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}




