package com.back.blog.category.controller;

import com.back.blog.category.domain.CategoryDomain;
import com.back.blog.category.service.CategoryService;
import com.back.blog.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ApiResponse<List<CategoryDomain>> getAllCategoryList(){
        return ApiResponse.success(categoryService.getAllCategoryList());
    }

}
