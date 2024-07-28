package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class TeamEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamEvaluationId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private TeamPost teamId;

    @ManyToOne
    @JoinColumn(name = "evaluatee_id")
    private Member evaluatee; // 평가받는사람

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private Member evaluator; // 평가하는사람

    //TODO 프로필과 일대다연결?
    private Long profileId;

    @Column(nullable = false)
    private int frequency;

    @Column(nullable = false)
    private int participate;

    @Column(nullable = false)
    private int kindness;

    @Column(nullable = false)
    private int promise;

    private double total;

}
