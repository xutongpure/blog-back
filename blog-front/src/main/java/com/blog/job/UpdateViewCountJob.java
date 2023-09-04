package com.blog.job;

import com.blog.domain.Article;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;
import com.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;
    @Scheduled(cron = "0 0 0/1 * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))   //构造方法
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
//        articleMapper.updateBatch(articles);
    }
}