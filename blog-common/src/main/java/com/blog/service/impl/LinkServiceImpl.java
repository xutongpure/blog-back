package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.Link;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddLinkDto;
import com.blog.domain.dto.UpdateLinkDto;
import com.blog.domain.vo.AdminGetLinkVo;
import com.blog.domain.vo.LinkListVo;
import com.blog.domain.vo.PageVo;
import com.blog.service.LinkService;
import com.blog.domain.Link;
import com.blog.mapper.LinkMapper;
import com.blog.utils.BeanCopyUtils;
import com.blog.domain.vo.LinkVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ResponseResult pageLinkList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.like(StringUtils.hasText(name),Link::getName,name);
        linkLambdaQueryWrapper.like(StringUtils.hasText(status),Link::getStatus,status);

        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,linkLambdaQueryWrapper);
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkListVo.class);
        return ResponseResult.okResult(new PageVo(linkListVos,page.getTotal()));
    }

    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLink(Long id) {
        Link link = getById(id);
        AdminGetLinkVo adminGetLinkVo = BeanCopyUtils.copyBean(link, AdminGetLinkVo.class);
        return ResponseResult.okResult(adminGetLinkVo);
    }

    @Override
    public ResponseResult updateLink(UpdateLinkDto updateLinkDto) {
        Link link = BeanCopyUtils.copyBean(updateLinkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delLink(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}




