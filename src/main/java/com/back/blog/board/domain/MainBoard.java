package com.back.blog.board.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MainBoard {
    
    // 게시글 번호
    private int boardNum;
    
    // 사용자 번호
    private int userNum;
    
    // 사용자 이름
    private String userName;
    
    // 게시글 제목
    private String boardTitle;
    
    // 작성일
    private Date boardCreateAt;
    
    // 수정일
    private Date boardUpdateAt;
    
    // 게시글 상태
    private int boardState;
    
    // 게시글 공감수
    private int boardFavCnt;
    
    // 게시글 조회수
    private int boardHitCnt;
}
