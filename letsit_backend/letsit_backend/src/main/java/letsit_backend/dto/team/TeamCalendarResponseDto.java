package letsit_backend.dto.team;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamCalendarResponseDto {
    private Long calendarId;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
}
