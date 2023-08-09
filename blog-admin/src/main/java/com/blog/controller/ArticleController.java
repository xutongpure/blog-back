package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddArticleDto;
import com.blog.domain.dto.ArticleListDto;
import com.blog.domain.dto.TagListDto;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) { //用Dto是为了可拓展
        return articleService.pageArticleList(pageNum,pageSize,articleListDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable("id") Long id) {
        return articleService.getAtricle(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.updateArticle(addArticleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        return articleService.delete(id);
    }


}
