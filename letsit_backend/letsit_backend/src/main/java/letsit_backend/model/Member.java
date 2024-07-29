package letsit_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private Long kakaoId;

    //@Column
    //private String email;

    @Column
    private String name;

    @Column
    private String ageRange;

    @Column
    private String gender;

    @Column
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    //@NotNull
    private Role role;

    @Builder
    public Member(String name, String profile_image_url, Role role, Long kakaoId, String gender, String age_range) {
        this.name = name;
        this.role = role;
        this.profileImageUrl = profile_image_url;
        this.ageRange = age_range;
        this.kakaoId = kakaoId;
        this.gender = gender;

    }






    // 가입일
    // 본인인증여부
}
