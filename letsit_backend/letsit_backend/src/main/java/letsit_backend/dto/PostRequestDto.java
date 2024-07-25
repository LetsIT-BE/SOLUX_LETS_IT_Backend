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
    private Post.PeopleNum peopleNum;
    private Timestamp recruitDueDate;
    private String preference;
    private ProjectInfo projectInfo;
    private List<String> stack;
    private Post.Difficulty difficulty;
    private Boolean onOff;
    private Long regionId;
    private Long categoryId;

//    @Getter
//    @Setter
//    @NoArgsConstructor
//    public static class RecruitDueDate {
//        private Timestamp startDate;
//        private Timestamp endDate;
//    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProjectInfo {
        private Post.ProjectPeriod projectPeriod;
        private Post.AgeGroup ageGroup;
    }


}