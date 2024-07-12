package letsit_backend.controller;

import letsit_backend.dto.ApplicantProfileDto;
import letsit_backend.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apply")
public class ApplyController {
    private final ApplyService applyService;

    @GetMapping(value = "/{postId}/list")
    public List<ApplicantProfileDto> getApplicantList(@PathVariable Long postId) {
        return applyService.getApplicantProfiles(postId);
    }
}
