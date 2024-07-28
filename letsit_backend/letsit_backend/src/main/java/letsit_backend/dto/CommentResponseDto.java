package letsit_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long userId;
    private String name;
    private String comContent;
    private Timestamp comCreateDate;
    private Timestamp comUpdateDate;
}
