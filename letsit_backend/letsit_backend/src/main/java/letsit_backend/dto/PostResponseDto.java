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
    private RecruitDueDate recruitDueDate;
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

    public PostResponseDto(Long postId, String title, String content, Post.PeopleNum peopleNum, RecruitDueDate recruitDueDate, String preference, ProjectInfo projectInfo, List<String> stack, Post.Difficulty difficulty, Boolean onOff, Boolean deadline, Long categoryId, Post.AgeGroup ageGroup, Timestamp createdAt, Timestamp updatedAt, int viewCount, int scrapCount) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.peopleNum = peopleNum;
        this.recruitDueDate = recruitDueDate;
        this.preference = preference;
        this.projectInfo = projectInfo;
        this.stack = stack;
        this.difficulty = difficulty;
        this.onOff = onOff;
        this.deadline = deadline;
        this.categoryId = categoryId;
        this.ageGroup = ageGroup;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.viewCount = viewCount;
        this.scrapCount = scrapCount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RecruitDueDate {
        private Timestamp startDate;
        private Timestamp endDate;

        public RecruitDueDate(Timestamp startDate, Timestamp endDate) {
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