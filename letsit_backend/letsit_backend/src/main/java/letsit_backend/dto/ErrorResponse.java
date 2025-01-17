package letsit_backend.dto;

import letsit_backend.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse {
    private final int errorCode;
    private final String error;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.message = errorCode.getMessage();
    }
}
