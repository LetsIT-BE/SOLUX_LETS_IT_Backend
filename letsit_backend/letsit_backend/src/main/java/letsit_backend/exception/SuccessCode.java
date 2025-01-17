package letsit_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    GET_SUCCESS(HttpStatus.OK, "조회 성공"),
    POST_SUCCESS(HttpStatus.CREATED, "게시 성공"),
    PATCH_SUCCESS(HttpStatus.OK, "업데이트 성공"),
    DELETE_SUCCESS(HttpStatus.NO_CONTENT, "삭제 성공");

    private final HttpStatus status;
    private final String message;
}
