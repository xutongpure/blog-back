package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.Article;
import com.blog.domain.Category;
import com.blog.domain.ResponseResult;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.domain.vo.ArticleListVo;
import com.blog.domain.vo.HotArticleVo;
import com.blog.domain.vo.PageVo;
import com.blog.domain.vo.ArticalDetailVo;
import com.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.blog.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
* @author xuton
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate  2023-04-22 15:04:12
*/
@Service
@SuppressWarnings("all")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {

    @Resource
    CategoryService categoryService;

    @Autowired
    RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成 ResponseResult 返回
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        lambdaQueryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        lambdaQueryWrapper.orderByAsc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(1,10);
        page(page,lambdaQueryWrapper);
        List<Article> artiles = page.getRecords();
        //Bean拷贝
//        List<HotArticleVo> hotArticleVos = new ArrayList<>();
//        for (Article article : artiles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            hotArticleVos.add(vo);
//        }
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(artiles,HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .map(article -> {
                    article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                    return article.getCategoryName();
                })
                .collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

//        System.out.println("==========================================");
//        System.out.println(page.getRecords().get(0).getCreateTime().getSecond());
//        System.out.println("==========================================");
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据文章id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //将Article转换成ArticleDetailVo
        ArticalDetailVo articalDetailVo = BeanCopyUtils.copyBean(article, ArticalDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articalDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category != null) {
            articalDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articalDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
}




