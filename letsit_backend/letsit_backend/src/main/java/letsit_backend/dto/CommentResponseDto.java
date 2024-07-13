package letsit_backend.dto;

import letsit_backend.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long userId;
    private String comContent;
    private Timestamp comCreateDate;
    private Timestamp comUpdateDate;

    public CommentResponseDto(Comment comment) {
        this.userId = comment.getUserId().getUserId();
        this.comContent = comment.getComContent();
        this.comCreateDate = comment.getComCreateDate();
        this.comUpdateDate = comment.getComUpdateDate();
    }
}
