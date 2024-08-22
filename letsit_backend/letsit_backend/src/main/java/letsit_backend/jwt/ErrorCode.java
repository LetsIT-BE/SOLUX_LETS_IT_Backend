package letsit_backend.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ID_DUPLICATED(HttpStatus.CONFLICT, "중복된 아이디입니다"),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "중복된 이메일입니다"),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "유저 인증에 실패했습니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 토큰 재발행을 요청해주세요"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 로그인을 다시 해주세요."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "인증에 필요한 JWT가 없습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을수없습니다."),

    // 이렇게 한개씩 분리할지 아니면 usernotfound는 한개로통일할지고민
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을수 없습니다."),
    TEAM_LEADER_NOT_FOUND(HttpStatus.NOT_FOUND, "팀리더를 찾을수 없습니다."),
    TEAM_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "팀멤버를 찾을수 없습니다."),
    TEAM_EVALUATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "평가자를 찾을수 없습니다."),
    TEAM_EVALUATEE_NOT_FOUND(HttpStatus.NOT_FOUND, "평가받는자를 찾을수 없습니다."),
    EVALUATION_DUPLICATED(HttpStatus.CONFLICT, "이미 평가했습니다."),
    CALENDAR_NOT_FOUND(HttpStatus.NOT_FOUND, "캘린더 일정을 찾을수없습니다.");


    private final HttpStatus status;
    private final String message;
}
