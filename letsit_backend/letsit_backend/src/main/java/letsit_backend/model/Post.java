package letsit_backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.antlr.v4.runtime.misc.Utils.count;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private int totalPersonnel;

    @ColumnDefault("0")
    private int currentPersonnel;

    // TODO 기간어떻게받을지...
    @Column(nullable = false)
    private Timestamp recruitDueDate;

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

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Area region;

    @ManyToOne
    @JoinColumn(name = "sub_region_id")
    private Area subRegion;

    // TODO 지역엔티티랑 매핑
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
    @CollectionTable(name = "post_stack", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "stack")
    private List<String> stack;


    // 마감여부 확인(기한 지났으면 + 마감true이면)
//    private boolean isClosed() {
//        return this.recruitPeriod.isBefore(LocalDate.now()) || this.deadline;
//    }



    /*
    모집자 관점) 신청자 승인 가능 여부
    public boolean isApprovable(Apply applicants) {
        return this.applicants.contains(applicants);
        // enum peopleNum 이랑 비교하는 로직
    }

    // 지원자 관점) 지원 가능 여부
    public boolean isApplyable(Long userId) {
        return !isClosed(); // && !이미 지원했는가?(isApplied)
    }
    public boolean iswithdrawAble(Long userId) {
        return !isClosed();
        // && 이미 지원했는가 && 이미수락됐는가
    }
     */

//    public void approval(Apply apply) {
//        if (!isClosed() && this.totalPersonnel > this.currentPersonnel) {
//        apply.approved();
//        currentPersonnel++;
//        }
//    }
//    public void reject(Apply apply) {
//        if (!isClosed()) {
//            apply.refused();
//        }
//    }
}
