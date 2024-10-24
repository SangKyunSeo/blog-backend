package com.back.blog.board.mapper;

import com.back.blog.board.domain.BoardDomain;
import com.back.blog.board.domain.MainBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    /**
     * 게시글 전체 조회
     * @return
     */
    List<BoardDomain> getAllBoardList();

    /**
     * 메인 페이지 게시글 조회
     * @param count
     * @return
     */
    List<MainBoard> getMainBoardList(int count);


}
