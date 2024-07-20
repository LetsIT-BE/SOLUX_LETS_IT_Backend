package letsit_backend.dto;

import letsit_backend.model.Comment;
import letsit_backend.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    //private Long userId;
    private String nickname;
    private String comContent;
    private Timestamp comCreateDate;
    private Timestamp comUpdateDate;

    public CommentResponseDto(Comment comment, ProfileRepository profile) {
        this.nickname = profile.findByUserId(comment.getUserId()).getNickname();
        this.comContent = comment.getComContent();
        this.comCreateDate = comment.getComCreateDate();
        this.comUpdateDate = comment.getComUpdateDate();
    }
}
