package com.back.blog.category.mapper;

import com.back.blog.category.domain.CategoryDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 전체 카테고리 조회
     * @return
     */
    List<CategoryDomain> getAllCategoryList();

}
