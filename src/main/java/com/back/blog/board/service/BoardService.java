package com.back.blog.board.service;

import com.back.blog.board.domain.MainBoard;
import com.back.blog.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    /**
     * 메인 페이지 게시글 조회 서비스
     * @param count
     * @return
     */
    public List<MainBoard> getMainBoardList(int count){
        log.info("<< 메인 페이지 게시글 조회 서비스 진입 >>");
        log.info("[요청 파라미터] {}", count);
        return boardMapper.getMainBoardList(count);
    }
}
