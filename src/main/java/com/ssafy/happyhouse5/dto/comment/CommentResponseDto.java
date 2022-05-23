package com.ssafy.happyhouse5.dto.comment;

import com.ssafy.happyhouse5.entity.Comment;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class CommentResponseDto {

    private Long id;
    private String memberId;
    private String title;
    private String content;
    private String updated;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.memberId = comment.getMember().getIdent();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.updated = comment.getModified()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
