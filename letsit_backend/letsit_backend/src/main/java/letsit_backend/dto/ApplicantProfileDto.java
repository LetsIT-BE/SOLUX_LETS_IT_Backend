package letsit_backend.dto;

import letsit_backend.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicantProfileDto {
    private String nickname;
    private String profileImage;

    public static ApplicantProfileDto fromEntity(Profile profile) {
        return new ApplicantProfileDto(profile.getNickname(), profile.getProfileImage());
    }
}
