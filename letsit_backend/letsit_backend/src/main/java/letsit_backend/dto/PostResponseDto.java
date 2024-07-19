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
    private Post.PeopleNum peopleNum;
    private recruitDueDate recruitDueDate;
    private String preference;
    private ProjectInfo projectInfo;
    private List<String> stack;
    private Post.Difficulty difficulty;
    private Boolean onOff;
    private Boolean deadline;
    private Long categoryId;
    private Post.AgeGroup ageGroup;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int viewCount;
    private int scrapCount;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class recruitDueDate {
        private Timestamp startDate;
        private Timestamp endDate;

        public recruitDueDate(Timestamp startDate, Timestamp endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProjectInfo {
        private String regionId;
        private Post.ProjectPeriod projectPeriod;
        private Post.AgeGroup ageGroup;

        public ProjectInfo(String regionId, Post.ProjectPeriod projectPeriod, Post.AgeGroup ageGroup) {
            this.regionId = regionId;
            this.projectPeriod = projectPeriod;
            this.ageGroup = ageGroup;
        }
    }
}