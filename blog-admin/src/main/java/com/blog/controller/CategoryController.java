package com.blog.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.blog.domain.Category;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddCategoryDto;
import com.blog.domain.dto.UpdateCategoryDto;
import com.blog.domain.vo.ExcelCategoryVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String status) {
        return categoryService.pageCategoryList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto) {
        return categoryService.addCategory(addCategoryDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategory(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto) {
        return categoryService.updateCategory(updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delCategory(@PathVariable("id") Long id) {
        return categoryService.delCategory(id);
    }
}
