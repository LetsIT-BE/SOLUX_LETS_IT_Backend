package letsit_backend.controller;

import jakarta.validation.Valid;
import letsit_backend.dto.Response;
import letsit_backend.dto.team.*;
import letsit_backend.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{postId}/create")
    public Response<?> creatTeam(@PathVariable Long postId,
                                 @RequestBody TeamCreateDto teamCreateDto) {

        // TODO 해당 게시글이 자신의 게시글인지 확인과정필요(자신의 글일때만 가능한기능)
        // TODO 팀게시판생성창에서 신청승인된 팀원목록 보여지는건 loadApplyConfirmUser()로 따로 구현?

        // TODO 일단 필수부분 구현하고, 보안로직 리펙토링하기(트랜젝션 동시성문제)
        // TODO testcode미작성
        Long teamPostId = teamService.creatTeam(postId, teamCreateDto);
        teamService.creatTeamMember(teamPostId); // 팀멤버 목록 보여주는기능은 따로 넘기기.
        return Response.success("팀게시판 생성 + 팀원등록 완료", teamPostId);
    }

    @GetMapping("/{teamId}/main")
    public Response<TeamInfoResponseDto> roadTeamInfo(@PathVariable Long teamId) {

        // TODO 팀장인사람->화면, 팀원인사람-> 화면구분어떻게다르게???


        // TODO 해당 팀게시판의 팀원인지 검증 -> false면 예외처리 / true면 정보출력
        // TODO 팀아이디정보, 팀멤버목록 반환 -> TeamService로 기능을 합칠지 고민

        return Response.success("팀게시판 메인", teamService.roadTeamInfo(teamId));
    }

    // TODO 팀장만(팀장의 userId를 받아와서 검증필요)
    @PatchMapping("/{teamId}/update")
    public Response<?> teamInfoUpdate(@PathVariable Long teamId,
                                      @RequestBody @Valid TeamUpdateRequestDto teamUpdateRequestDto) {

        // TODO 팀아이디 불러오기
        // TODO 팀원이 해당 팀의 리더인지 검증
        teamService.teamInfoUpdate(teamId,teamUpdateRequestDto);
        return Response.success("회의인증->프로젝트정보수정버튼(프로젝트관리)", null);
    }


    @PatchMapping("/{teamId}/{userId}")
    public Response<?> teamLeaderChange(@PathVariable Long teamId,
                                        @PathVariable Long userId) {


        teamService.changeTeamLeader(teamId,userId);
        return Response.success("팀정보수정->팀장위임", null);
    }

    @PostMapping("/evaluation/{teamId}/{userId}")
    public Response<?> evaluation(@PathVariable Long teamId,
                                  @PathVariable Long userId,
                                  @RequestBody TeamEvaluationRequestDto teamEvaluationRequestDto) {

        // TODO 팀원평가랑 팀원신고기능에서 팀원목록 리스트업은 어떻게 처리하는지??->팀원로드기능따로구현?
        // TODO 팀멤버인지 검증(authentication객체 == teamMember and teamMember == teamId)

        // 팀원평가결과 종합해서 프로필에 보여주는 로직도필요함.
        teamService.teamEvaluation(teamId, userId, teamEvaluationRequestDto);
        return Response.success("팀원평가", null);
    }

    // TODO 팀장만(팀장의 userId를 받아와서 검증필요)
    @PatchMapping("/{teamId}/complete")
    public Response<?> projectComplete(@PathVariable Long teamId) {

        // TODO 팀게시판, 유저아이디 -> 팀장인지검증
        teamService.projectComplete(teamId);
        return Response.success("프로젝트종료", null);
    }


    // ---- 필수x ----

    // TODO 팀장만 (개발시작!)
    @GetMapping("/{teamId}/meeting")
    public Response<?> meetingCertification(@PathVariable Long teamId,
                                            @RequestBody TeamMeetingCertificationRequestDto requestDto) {

        // TODO 이미지정보, 불참팀원 받아옴
        // TODO OpenCv랑 연결 -> 인증완료시 true반환

        //boolean isVerified =  teamService.meetingCertification(teamId, requestDto);
        return Response.success("프로젝트관리->회의인증버튼", null);
    }

    @PostMapping("/{teamId}/report")
    public Response<?> userGetout() {

        // TODO 해당팀의 팀원인지 검증
        // TODO teamReportDto로 팀원정보, 강퇴사유전달
        // TODO 강퇴엔티티에 데이터생성(강퇴동의수는 1로생성)

        return Response.success("팀원설정->강퇴제안", null);
    }

    // TODO 강퇴제안된 팀원 보여주기 기능???(강퇴엔티티리스트업)

    @PutMapping("/{teamId}/agree")
    public Response<?> userGetoutAgree() {

        // TODO 경로를 /{teamId}/{reported_user}/agree 로 해야할듯
        // TODO 팀멤버인지 검증
        // TODO 팀아이디, 강퇴회원정보, true/false받아오기 -> 어떻게? 강퇴자리스트업 목록이랑 연결?
        // TODO true일때 강퇴동의수 +1
        // TODO 투표완료한사람은 두번다시 투표안되게할지??? 계속바꿀수있는지??? -> 다 동의되면 자동강퇴인지??

        return Response.success("강퇴투표 동의/비동의 기능", null);
    }

    @PostMapping("/{teamId}/resign")
    public Response<?> resign() {

        // TODO TeamId, dto전달
        // TODO TeamResignDto로 신고할팀원 전달
        // TODO TeamResign 엔티티 추가후 모아두기.

        // 추후에 신고가 3회이상쌓이면 불이익추기

        return Response.success("신고기능", null);
    }

    // TODO 팀원정보보기 -> 프로필담당자
    // TODO 팀장위임기능 -> 정보수정과 함께?

}
