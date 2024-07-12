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
public class PostRequestDto {
    private String title;
    private String content;
    private int peopleNum;
    private RecruitPeriod recruitPeriod;
    private String preference;
    private ProjectInfo projectInfo;
    private List<String> stack;
    private Post.Difficulty difficulty;
    private Boolean onOff;
    private Long regionId;
    private Long categoryId;


    @Getter
    @Setter
    @NoArgsConstructor
    public static class RecruitPeriod {
        private Timestamp startDate;
        private Timestamp endDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProjectInfo {
        private Post.projectPeriod projectPeriod;
        private Post.AgeGroup ageGroup;
    }
}