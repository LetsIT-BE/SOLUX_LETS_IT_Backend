package letsit_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private int peopleNum;
    private PostRequestDto.RecruitPeriod recruitPeriod;
    private String preference;
    private PostRequestDto.ProjectInfo projectInfo;
    private List<String> stack;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}