package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddLinkDto;
import com.blog.domain.dto.UpdateLinkDto;
import com.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String status) {
        return linkService.pageLinkList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto) {
        return linkService.addLink(addLinkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable("id") Long id) {
        return linkService.getLink(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody UpdateLinkDto updateLinkDto) {
        return linkService.updateLink(updateLinkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delLink(@PathVariable("id") Long id) {
        return linkService.delLink(id);
    }
}
