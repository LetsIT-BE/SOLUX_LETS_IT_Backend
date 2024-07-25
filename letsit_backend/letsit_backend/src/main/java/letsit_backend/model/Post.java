package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private PeopleNum peopleNum;
    public enum PeopleNum {
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT
    }
//    @Column(nullable = false)
//    private Timestamp recruitPeriodStart;
//
    @Column(nullable = false)
    private Timestamp recruitDueDate;

//    @Column(nullable = false)
//    private Timestamp projectPeriodStart;
//
//    @Column(nullable = false)
//    private Timestamp projectPeriodEnd;

    @Enumerated(EnumType.STRING)
    private ProjectPeriod projectPeriod;
    public enum ProjectPeriod {
        oneMonth,
        twoMonths,
        threeMonths,
        fourMonths,
        fiveMonths
    }

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    public enum Difficulty {
        beginner,
        basic,
        mid,
        advanced
    }


    private Boolean onOff;

//    // TODO 지역엔티티랑 매핑
//    private Long regionId;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Area region;

    @ManyToOne
    @JoinColumn(name = "sub_region_id")
    private Area subRegion;

    // TODO 분야엔티티랑 매핑
    private Long categoryId;

    private int viewCount;

    private int scrapCount;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(nullable = false)
    private Boolean deadline;

    @ElementCollection
    private List<String> stack;

    private String preference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgeGroup ageGroup;

    public enum AgeGroup {
        s10, //10 대
        s20a, //20대 초
        s20b,  // 20대 중
        s20c, // 20대 후
        s30 // 30대
    }

//    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @OrderBy("commentId asc")
//    private List<Comment> comments;

    public void setDeadline(Boolean deadline) {
        this.deadline = deadline;
    }
}
