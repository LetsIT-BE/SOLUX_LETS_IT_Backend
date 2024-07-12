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
    private int viewCount;
    private int scrapCount;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RecruitPeriod {
        private Timestamp startDate;
        private Timestamp endDate;

        public RecruitPeriod(Timestamp startDate, Timestamp endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProjectInfo {
        private String regionId;
        private Post.projectPeriod projectPeriod;
        private Post.AgeGroup ageGroup;

        public ProjectInfo(String regionId, Post.projectPeriod projectPeriod, Post.AgeGroup ageGroup) {
            this.regionId = regionId;
            this.projectPeriod = projectPeriod;
            this.ageGroup = ageGroup;
        }
    }
}