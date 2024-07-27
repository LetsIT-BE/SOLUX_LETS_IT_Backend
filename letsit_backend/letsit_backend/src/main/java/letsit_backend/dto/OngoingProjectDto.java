package letsit_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OngoingProjectDto {
    private Long teamId;
    private String prjTitle;
    private List<String> profileImages;
}
