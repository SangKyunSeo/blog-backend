package com.back.blog.category.domain;

import lombok.Data;

@Data
public class CategoryDomain {
    
    // 카테고리 번호
    private int categoryNum;
    
    // 카테고리 이름
    private String categoryName;
    
    // 카테고리 상태
    private int categoryState;

}
