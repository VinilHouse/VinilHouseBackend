package com.ssafy.happyhouse5.dto.board;

import com.ssafy.happyhouse5.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String memberIdent;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.memberIdent = board.getMember().getIdent();
    }
}
