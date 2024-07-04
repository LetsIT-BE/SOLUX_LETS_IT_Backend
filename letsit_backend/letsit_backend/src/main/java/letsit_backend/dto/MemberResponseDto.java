package letsit_backend.dto;

import letsit_backend.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private String loginId;
    private String username;

    public static MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(member.getLoginId(), member.getName());
    }
}
