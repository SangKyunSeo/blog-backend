package com.back.blog.board.dto.request;

import lombok.Data;

@Data
public class RequestBoardWrite {
    
    // 제목
    private String boardTitle;
    
    // 내용
    private String boardContent;
    
    // 카테고리 번호
    private int categoryNum;
    
    // 사용자 아이디
    private String userId;
}
