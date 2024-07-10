package letsit_backend.dto;

import letsit_backend.model.Post;
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
    private String title;
    private String content;
    private int peopleNum;
    private PostRequestDto.RecruitPeriod recruitPeriod;
    private String preference;
    private PostRequestDto.ProjectInfo projectInfo;
    private List<String> stack;
    private Post.Difficulty difficulty;
    private Boolean onOff;
    private Long categoryId;
    private Post.AgeGroup ageGroup;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}