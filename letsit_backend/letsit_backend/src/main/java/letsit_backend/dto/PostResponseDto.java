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
    private Long userId;
    private Long postId;
    private String title;
    private String content;
    private Post.PeopleNum peopleNum;
    private Timestamp recruitDueDate;
    private String preference;
//    private ProjectInfo projectInfo;
    private List<String> stack;
    private Post.Difficulty difficulty;
    private Post.OnOff onOff;
    private Boolean deadline;
    private List<String> categoryId;
    private Post.AgeGroup ageGroup;
    private String region;
    private String subRegion;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int viewCount;
    private int scrapCount;
    private Post.ProjectPeriod projectPeriod;

    public PostResponseDto(Long userId, Long postId, String title, String content, Post.PeopleNum peopleNum, Timestamp recruitDueDate, String preference/*, ProjectInfo projectInfo*/, List<String> stack, Post.Difficulty difficulty, Post.OnOff onOff, Boolean deadline, List<String> categoryId, Post.AgeGroup ageGroup, String region, String subRegion, Timestamp createdAt, Timestamp updatedAt, int viewCount, int scrapCount, Post.ProjectPeriod projectPeriod) {
        this.userId = userId;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.peopleNum = peopleNum;
        this.recruitDueDate = recruitDueDate;
        this.preference = preference;
//        this.projectInfo = projectInfo;
        this.stack = stack;
        this.difficulty = difficulty;
        this.onOff = onOff;
        this.deadline = deadline;
        this.categoryId = categoryId;
        this.ageGroup = ageGroup;
        this.region = region;
        this.subRegion = subRegion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.viewCount = viewCount;
        this.scrapCount = scrapCount;
        this.projectPeriod = projectPeriod;
    }

//    @Getter
//    @Setter
//    @NoArgsConstructor
//    public static class RecruitDueDate {
//        private Timestamp startDate;
//        private Timestamp endDate;
//
//        public RecruitDueDate(Timestamp startDate, Timestamp endDate) {
//            this.startDate = startDate;
//            this.endDate = endDate;
//        }
//
//    }

//    @Getter
//    @Setter
//    @NoArgsConstructor
//    public static class ProjectInfo {
//        private String regionId;
//        private String subRegionId;
//        private Post.ProjectPeriod projectPeriod;
//        private Post.AgeGroup ageGroup;
//
//        public ProjectInfo(String regionId, String subRegionId, Post.ProjectPeriod projectPeriod, Post.AgeGroup ageGroup) {
//            this.regionId = regionId;
//            this.subRegionId = subRegionId;
//            this.projectPeriod = projectPeriod;
//            this.ageGroup = ageGroup;
//        }
//    }
}