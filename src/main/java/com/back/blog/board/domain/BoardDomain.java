package com.back.blog.board.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BoardDomain {
    private int boardNum;
    private int userNum;
    private int categoryNum;
    private String boardTitle;
    private String boardContent;
    private Date boardCreateAt;
    private Date boardUpdateAt;
    private int boardState;
}
