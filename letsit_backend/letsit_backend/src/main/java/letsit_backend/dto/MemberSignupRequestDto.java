package letsit_backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import letsit_backend.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberSignupRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotNull(message = "생년월일을 입력하세요.")
    private String birth;
    @NotNull(message = "성별을 입력하세요.")
    private String gender;

    @Builder
    public Member toEntity() {
        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .email(this.email)
                .name(this.name)
                .birth(this.birth)
                .gender(this.gender)
                .build();
    }
}
