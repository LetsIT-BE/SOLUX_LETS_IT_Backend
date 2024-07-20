package letsit_backend.dto;

import letsit_backend.model.Apply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class ApplyResponseDto {
    private Long userId;
    private String preferStack;
    private String desiredField;
    private String applyContent;
    private Timestamp applyCreatDate;
    public ApplyResponseDto(Apply apply) {
        this.userId = apply.getUserId().getUserId();
        this.preferStack = apply.getPreferStack();
        this.desiredField = apply.getDesiredField();
        this.applyContent = apply.getApplyContent();
        this.applyCreatDate = apply.getApplyCreatDate();
    }
}
