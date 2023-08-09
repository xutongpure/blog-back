package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.domain.Article;
import com.blog.domain.Category;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddCategoryDto;
import com.blog.domain.dto.UpdateCategoryDto;
import com.blog.domain.vo.AdminGetCategoryVo;
import com.blog.domain.vo.CategoryListVo;
import com.blog.domain.vo.PageVo;
import com.blog.mapper.CategoryMapper;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.domain.vo.CategoryVo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category>
        implements CategoryService {
    @Resource
    @Lazy   //避免循环依赖
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {

        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().filter(category -> category.getStatus().equals(SystemConstants.STATUS_NORMAL))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult pageCategoryList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.like(StringUtils.hasText(name),Category::getName,name);
        categoryLambdaQueryWrapper.like(StringUtils.hasText(status),Category::getStatus,status);

        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,categoryLambdaQueryWrapper);
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryListVo.class);
        return ResponseResult.okResult(new PageVo(categoryListVos,page.getTotal()));
    }

    @Override
    public ResponseResult addCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Long id) {
        Category category = getById(id);
        AdminGetCategoryVo adminGetCategoryVo = BeanCopyUtils.copyBean(category, AdminGetCategoryVo.class);
        return ResponseResult.okResult(adminGetCategoryVo);
    }

    @Override
    public ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto) {
        Category category = BeanCopyUtils.copyBean(updateCategoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
