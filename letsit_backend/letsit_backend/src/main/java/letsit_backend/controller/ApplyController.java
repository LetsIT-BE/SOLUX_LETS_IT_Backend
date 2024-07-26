//package letsit_backend.controller;
//
//import letsit_backend.dto.ApplicantProfileDto;
//import letsit_backend.dto.ApplyRequestDto;
//import letsit_backend.dto.ApplyResponseDto;
//import letsit_backend.dto.Response;
//import letsit_backend.service.ApplyService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/apply")
//public class ApplyController {
//    private final ApplyService applyService;
//
//    @PostMapping(value = "/{postId}/write/{userId}")
//    public Response<ApplyResponseDto> postNewApply(@PathVariable Long postId, @PathVariable Long userId, @RequestBody ApplyRequestDto request) {
//        ApplyResponseDto submittedApply = applyService.create(postId, userId, request);
//        return Response.success("성공", submittedApply);
//    }
//
//    @GetMapping("/{applyId}")
//    public Response<ApplyResponseDto> getApply(@PathVariable Long applyId) {
//        ApplyResponseDto apply = applyService.read(applyId);
//        return Response.success("지원서 보기", apply);
//    }
//
//
//    @DeleteMapping("/{applyId}/delete")
//    public Response<String> deleteApply(@PathVariable Long applyId) {
//        applyService.delete(applyId);
//        return Response.success("지원서를 삭제하였습니다", null);
//    }
//
//    @GetMapping(value = "/{postId}/list")
//    public Response<List<ApplicantProfileDto>> getApplicantList(@PathVariable Long postId) {
//        List<ApplicantProfileDto> applicant = applyService.getApplicantProfiles(postId);
//        return Response.success("지원자 리스트", applicant);
//    }
//
//    @GetMapping(value = "/{postId}/approvedlist")
//    public Response<List<ApplicantProfileDto>> getApprovedApplicantList(@PathVariable Long postId) {
//        List<ApplicantProfileDto> approved =  applyService.getApprovedApplicantProfiles(postId);
//        return Response.success("승인된 지원자 리스트", approved);
//    }
//
//
//    @PatchMapping(value = "/{postId}/list/{applyId}/approval")
//    public Response<String> approvalApplicant(@PathVariable Long postId, @PathVariable Long applyId) {
//        applyService.approveApplicant(postId, applyId);
//        log.info("Approval request received for Post ID: {} and Apply ID: {}", postId, applyId);
//        //log.info("Application approved successfully for Apply ID: {}", applyId);
//        return Response.success("지원서가 승인되었습니다.", null);
//    }
//
//    @PatchMapping(value = "/{postId}/list/{applyId}/reject")
//    public ResponseEntity<String> rejectionApplicant(@PathVariable Long postId, @PathVariable Long applyId) {
//        applyService.rejectApplicant(postId, applyId);
//        log.info("Approval request received for Post ID: {} and Apply ID: {}", postId, applyId);
//        //log.info("Application approved successfully for Apply ID: {}", applyId);
//        return ResponseEntity.status(HttpStatus.OK).body("지원서가 거절되었습니다.");
//    }
//}
