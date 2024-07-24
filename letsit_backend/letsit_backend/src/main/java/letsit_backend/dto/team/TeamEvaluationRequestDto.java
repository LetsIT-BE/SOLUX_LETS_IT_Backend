package letsit_backend.dto.team;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TeamEvaluationRequestDto {
    private int frequency;
    private int participate;
    private int kindness;
    private int promise;

    @Builder
    public TeamEvaluationRequestDto(int frequency, int participate, int kindness, int promise) {
        this.frequency = frequency;
        this.participate = participate;
        this.kindness = kindness;
        this.promise = promise;
    }
}
