package letsit_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private Member userId;

    @Enumerated(EnumType.STRING)
    private Manner_tier mannerTier;
    public enum Manner_tier {
        S,
        A,
        B,
        C,
        F
    }

    private double mannerScore;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Age age;
    public enum Age {
        under20,
        under30,
        over30,
    }

    private String profileUrl;

    private String profileImage;

    private String bio;

    private String selfIntro;

    public void mannserScoreUpdate(double mannerScore) {
        this.mannerScore = mannerScore;
    }
}
