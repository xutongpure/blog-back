package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.AddCategoryDto;
import com.blog.domain.dto.UpdateCategoryDto;

/**
* @author xuton
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2023-05-09 21:17:22
*/
public interface CategoryService extends IService<Category> {

    ResponseResult<Category> getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult pageCategoryList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(AddCategoryDto addCategoryDto);

    ResponseResult getCategory(Long id);

    ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto);

    ResponseResult delCategory(Long id);
}
