package letsit_backend.dto.profile;

import jakarta.validation.constraints.Size;
import letsit_backend.model.Member;
import letsit_backend.model.Profile;
import lombok.Data;

import java.util.Map;

@Data
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
    private Profile.Age age;
    /*
    public enum Age {
        teens,
        twenties,
        overThirties
    }

     */
    private String sns;
    private String profile_image_url;
    @Size(max = 20, message = "Bio must be up to 20 characters long")
    private String bio;
    private String selfIntro;
    private Map<String, Integer> skills;

}
