package com.ssafy.happyhouse5.dao;

import com.ssafy.happyhouse5.dto.board.Board;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDao {

    void create(Board board);

    void update(Board board);

    void delete(Board board);

    Board selectById(int id);

    List<Board> findLikeTitle(String id);

    List<Board> findLikeContent(String content);

    List<Board> findByMemberId(String memberId);

    List<Board> selectAll();
}
