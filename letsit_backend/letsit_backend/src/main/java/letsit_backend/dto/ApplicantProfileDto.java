package letsit_backend.dto;

import letsit_backend.model.Apply;
import letsit_backend.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicantProfileDto {
    private Long applyId;
    private String nickname;
    private String profileImage;

    public static ApplicantProfileDto fromEntity(Profile profile, Apply apply) {
        return new ApplicantProfileDto(apply.getApplyId(), profile.getNickname(), profile.getProfileImage());
    }
}
