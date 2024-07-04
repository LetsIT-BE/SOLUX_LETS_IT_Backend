package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // TODO 해당문법의 쓰임확인하기
@Entity
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @ManyToOne // 다대일
    @JoinColumn(name = "POST_ID")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member userId;

    // 포지션
    @Column(nullable = false)
    private String applyContent;

    @Column(nullable = false)
    private Timestamp applyCreatDate;

    private Boolean confirm;

}
