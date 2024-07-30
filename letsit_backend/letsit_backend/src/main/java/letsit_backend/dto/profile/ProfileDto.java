package letsit_backend.dto.profile;

import jakarta.validation.constraints.Size;
import letsit_backend.model.Member;
import letsit_backend.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
@Getter
@AllArgsConstructor
public class ProfileDto {
    private Long profileId;
    private Long userId;
    private Profile.Manner_tier mannerTier;
    /*
    public enum Manner_tier {
        S,
        A,
        B,
        C,
        F
    }

     */
    private int mannerScore;
    private String nickname;
    private String age;
    /*
    public enum Age {
        teens,
        twenties,
        overThirties
    }

     */
    private String sns;
    private String profileImageUrl;
    @Size(max = 20, message = "Bio must be up to 20 characters long")
    private String bio;
    private String selfIntro;
    private Map<String, Integer> skills;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
