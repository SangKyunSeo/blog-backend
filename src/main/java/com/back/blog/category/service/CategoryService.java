package com.back.blog.category.service;

import com.back.blog.category.domain.CategoryDomain;
import com.back.blog.category.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 전체 카테고리 조회 서비스
     * @return
     */
    public List<CategoryDomain> getAllCategoryList(){
        log.info("<< 전체 카테고리 조회 서비스 진입 >>");

        return categoryMapper.getAllCategoryList();
    }
}
