package letsit_backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
        TWO("2명"),
        THREE("3명"),
        FOUR("4명"),
        FIVE("5명"),
        SIX("6명"),
        SEVEN("7명"),
        EIGHT("8명");

        private final String korean;

        PeopleNum(String korean) {
            this.korean = korean;
        }

        @JsonValue
        public String getKorean() {
            return korean;
        }

        @JsonCreator
        public static PeopleNum fromKorean(String korean) {
            for (PeopleNum num : PeopleNum.values()) {
                if (num.korean.equals(korean)) {
                    return num;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + korean);
        }
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
        oneMonth("1개월"),
        twoMonths("2개월"),
        threeMonths("3개월"),
        fourMonths("4개월");

        private final String korean;

        ProjectPeriod(String korean) {
            this.korean = korean;
        }

        @JsonValue
        public String getKorean() {
            return korean;
        }

        @JsonCreator
        public static ProjectPeriod fromKorean(String korean) {
            for (ProjectPeriod period : ProjectPeriod.values()) {
                if (period.korean.equals(korean)) {
                    return period;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + korean);
        }
    }

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    public enum Difficulty {
        BEGINNER("입문"),
        BASIC("초급"),
        MID("중급"),
        ADVANCED("고급");

        private final String korean;

        Difficulty(String korean) {
            this.korean = korean;
        }

        @JsonValue
        public String getKorean() {
            return korean;
        }

        @JsonCreator
        public static Difficulty fromKorean(String korean) {
            if (korean == null || korean.isEmpty()) {
                throw new IllegalArgumentException("Empty string is not a valid value for Difficulty");
            }
            for (Difficulty difficulty : Difficulty.values()) {
                if (difficulty.korean.equals(korean)) {
                    return difficulty;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + korean);
        }
    }

    @Enumerated(EnumType.STRING)
//    private Boolean onOff;
    private OnOff onOff;
    public enum OnOff {
        ON("대면"),
        OFF("비대면");

        private final String korean;

        OnOff(String korean) {
            this.korean = korean;
        }

        @JsonValue
        public String getKorean() {
            return korean;
        }

        @JsonCreator
        public static OnOff fromKorean(String korean) {
            if (korean == null || korean.isEmpty()) {
                throw new IllegalArgumentException("Empty string is not a valid value for OnOff");
            }
            for (OnOff onOff : OnOff.values()) {
                if (onOff.korean.equals(korean)) {
                    return onOff;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + korean);
        }
    }

    // TODO 지역엔티티랑 매핑
//    private Long regionId;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Area region;

    @ManyToOne
    @JoinColumn(name = "sub_region_id")
    private Area subRegion;

    // TODO 분야엔티티랑 매핑
    @ElementCollection
    @CollectionTable(name = "post_category", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "category_id")
    private List<String> categoryId;

    private int viewCount;

    private int scrapCount;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(nullable = false)
    private Boolean deadline;

    @ElementCollection
    @CollectionTable(name = "post_stack", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "stack")
    private List<String> stack;

    private String preference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgeGroup ageGroup;

    public enum AgeGroup {
        S10("10대"),
        S20A("20대"),
        S20B("30대"),
        S20C("40대 이상");

        private final String korean;

        AgeGroup(String korean) {
            this.korean = korean;
        }

        @JsonValue
        public String getKorean() {
            return korean;
        }

        @JsonCreator
        public static AgeGroup fromKorean(String korean) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                if (ageGroup.korean.equals(korean)) {
                    return ageGroup;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + korean);
        }
    }

//    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @OrderBy("commentId asc")
//    private List<Comment> comments;

    public void setDeadline(Boolean deadline) {
        this.deadline = deadline;
    }
}
