package com.back.blog.board.controller;

import com.back.blog.board.domain.MainBoard;
import com.back.blog.board.service.BoardService;
import com.back.blog.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 메인 페이지 게시글 조회
    @GetMapping("/main/list")
    public ApiResponse<List<MainBoard>> getMainBoardList(){
        log.debug("<< 메인 페이지 게시글 조회 컨트롤러 진입 >>");
        int count = 6; // 메인 페이지에 보여줄 게시글 개수 선택
        return ApiResponse.success(boardService.getMainBoardList(count));
    }

}
