package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.title like %:title%")
    List<Board> findLikeTitle(@Param("title") String title);

    @Query("select b from Board b where b.content like %:content%")
    List<Board> findLikeContent(@Param("content") String content);

    @Query("select b from Board b where b.member.ident like %:ident%")
    List<Board> findByMemberId(@Param("ident") String ident);
}
