package letsit_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import letsit_backend.config.MapToJsonConverter;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;

@Builder
@Getter @Setter
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

    private int mannerScore;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Age age;
    public enum Age {
        teens,
        twenties,
        overThirties
    }

    private String sns;

    private String profile_image_url;

    @Column(length = 20)
    @Size(max = 20, message = "Bio must be up to 20 characters long")
    private String bio;

    private String selfIntro;

    @Lob
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Integer> skills;

}
