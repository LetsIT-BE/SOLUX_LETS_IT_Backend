package letsit_backend.controller;

import letsit_backend.dto.ApplicantProfileDto;
import letsit_backend.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/apply")
public class ApplyController {
    private final ApplyService applyService;

    @GetMapping(value = "/{postId}/list")
    public List<ApplicantProfileDto> getApplicantList(@PathVariable Long postId) {
        return applyService.getApplicantProfiles(postId);
    }
    /*
    @PostMapping(value = "/{postId}/write/{userId}")
    public ResponseEntity<Apply> postNewApply(@PathVariable Long postId, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body();
    }


     */

    @PatchMapping(value = "/{postId}/list/{applyId}/approval")
    public ResponseEntity<String> approvalApplicant(@PathVariable Long postId, @PathVariable Long applyId) {
        applyService.approveApplicant(postId, applyId);
        log.info("Approval request received for Post ID: {} and Apply ID: {}", postId, applyId);
        //log.info("Application approved successfully for Apply ID: {}", applyId);
        return ResponseEntity.status(HttpStatus.OK).body("지원서가 승인되었습니다.");
    }

    @PatchMapping(value = "/{postId}/list/{applyId}/reject")
    public ResponseEntity<String> rejectionApplicant(@PathVariable Long postId, @PathVariable Long applyId) {
        applyService.rejectApplicant(postId, applyId);
        log.info("Approval request received for Post ID: {} and Apply ID: {}", postId, applyId);
        //log.info("Application approved successfully for Apply ID: {}", applyId);
        return ResponseEntity.status(HttpStatus.OK).body("지원서가 거절되었습니다.");
    }
}
