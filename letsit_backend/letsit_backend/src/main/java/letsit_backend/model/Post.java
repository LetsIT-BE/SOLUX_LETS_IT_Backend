package letsit_backend.model;

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
    private LocalDate recruitPeriod;
    private Timestamp projectPeriod;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    public enum Difficulty {
        beginner,
        basic,
        mid,
        advanced
    }


    private Boolean onOff;

    // TODO 지역엔티티랑 매핑
    private Long regionId;

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

    // TODO 요구스택 엔티티랑 매핑
    private Long stackId;


    // 마감여부 확인(기한 지났으면 + 마감true이면)
    private boolean isClosed() {
        return this.recruitPeriod.isBefore(LocalDate.now()) || this.deadline;
    }



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

    public void approval(Apply apply) {
        if (!isClosed() && this.totalPersonnel > this.currentPersonnel) {
        apply.approved();
        currentPersonnel++;
        }
    }
    public void reject(Apply apply) {
        if (!isClosed()) {
            apply.refused();
        }
    }

    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("commentId asc")
    private List<Comment> comments;
}
