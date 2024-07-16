package letsit_backend.service;

import letsit_backend.dto.team.TeamCreateDto;
import letsit_backend.dto.team.TeamEvaluationRequestDto;
import letsit_backend.dto.team.TeamInfoResponseDto;
import letsit_backend.dto.team.TeamUpdateRequestDto;
import letsit_backend.model.*;
import letsit_backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final PostRepository postRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamPostRepository teamPostRepository;
    private final ApplyRepository applyRepository;
    private final TeamEvaluationRepository teamEvaluationRepository;
    private final MemberRepository memberRepository;

    // TODO 생성자주입, setter사용지향, 빌드주입하기로 수정필요

    // 팀 게시판 생성
    @Transactional
    public Long creatTeam(Long postId, TeamCreateDto teamCreateDto) {

        // TODO userId 체크

        // 팀게시판 생성로직
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalIdentifierException("게시글을 찾을수없음."));

        TeamPost newTeamPost = new TeamPost(
                post,
                teamCreateDto.getTeamName(),
                teamCreateDto.getNotionLink(),
                teamCreateDto.getGithubLink());

        teamPostRepository.save(newTeamPost); // 저장
        return newTeamPost.getTeamId();

    }

    // 팀멤버 생성
    @Transactional
    public void creatTeamMember(Long teamPostId) {

        // TODO 해당 게시글id의 apply승인된사람 정보 받아오기 -> 모집인원과 list길이일치하는지 검증필요
        // TODO 코드 리펙토링하기(teampost로 로직수행해도되는지, postid가져와서하는게 안전한지)
        // Post post = PostRepository.findById.orElseThrow(()->new RuntimeException("this is not found");
        Optional<TeamPost> teamPostOptional = teamPostRepository.findById(teamPostId);
        TeamPost teamPost = teamPostRepository.findById(teamPostId)
                .orElseThrow(()-> new RuntimeException("팀게시글을 찾을수없음"));


        Post post = teamPost.getPostId();              //post불러옴
        // 신청 승인된자 불러오기
        List<Apply> applies = applyRepository.findAllByPostId(post);
        for (Apply applie : applies) {
            if (applie.getConfirm()) {                // 지원서 승인된사람불러옴.
                // 팀원목록 저장
                Member member = applie.getUserId();   // member객체소환
                TeamMember teamMember = new TeamMember(teamPost, member, TeamMember.Role.Team_Member);
                teamMemberRepository.save(teamMember);
            }
        }

        // 팀장 저장
        Member postUserId = post.getUserId();
        TeamMember teamLeader = new TeamMember(teamPost, postUserId, TeamMember.Role.Team_Leader);
        teamMemberRepository.save(teamLeader);

    }

    // 팀정보 불러오기
    public TeamInfoResponseDto roadTeamInfo(Long teamId) {
        TeamPost teamPost = teamPostRepository.findById(teamId)
                .orElseThrow(()-> new RuntimeException("team is not found"));

        List<TeamMember> teamMemberList = teamMemberRepository.findAllByTeamId(teamPost);
        // TODO 팀멤버리스트 없을시 예외처리 필요
        List<Map<String, TeamMember.Role>> teamInfoList = new ArrayList<>();
        for (TeamMember teamMember : teamMemberList) {
            teamInfoList.add(Map.of(teamMember.getUserId().getName(), teamMember.getTeamMemberRole()));
        }

        TeamInfoResponseDto teamInfoResponseDto =
                new TeamInfoResponseDto(teamPost.getPrjTitle(),
                                        teamPost.getNotionLink(),
                                        teamPost.getGithubLink(),
                                        teamInfoList);

        return teamInfoResponseDto;

    }

    // 팀정보 수정
    @Transactional
    public void teamInfoUpdate(Long teamId, TeamUpdateRequestDto teamUpdateRequestDto) {

        // TODO 더티채킹
        // FIXME null값을 의도적으로 주입했을때 없애는 방법이없는 오류 수정
        TeamPost teamPost = teamPostRepository.findById(teamId)
                .orElseThrow(()-> new IllegalIdentifierException("team is not found"));
        teamPost.TeamUpdate(teamUpdateRequestDto.getTeamName(),
                        teamUpdateRequestDto.getGithubLink(),
                        teamUpdateRequestDto.getNotionLink());

        // TODO 팀장위임기능(따로함수분리하기)
        // TODO 팀원정보 선택후 changeLeader로 user정보 전달
        // TODO TeamId와 User로 TeamMember에서 유저찾기
        // TODO TeamLeader로 해당유저 역할 팀장으로 변경
        // TODO 본인id(권한집행자)를 TeamId, User로 TeamMember에서 찾아서 팀원으로변경

    }

    // TODO 팀장변경기능 미완성상태
    @Transactional
    public void changeTeamLeader(Long teamId, Long userId) {
        // 팀장이 될 팀원의 teamMember 찾기
        TeamPost teamPost = teamPostRepository.findById(teamId)
                .orElseThrow(()-> new IllegalIdentifierException("team is not found"));
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new IllegalIdentifierException("user is not found"));
        TeamMember changeLeader = teamMemberRepository.findByTeamIdAndUserId(teamPost, member)
                .orElseThrow(()-> new IllegalIdentifierException("team is not found"));

        changeLeader.setTeamMemberRole(TeamMember.Role.Team_Leader);
        teamMemberRepository.save(changeLeader);

        // 팀장의 역할을 팀원으로변경
        // TODO 팀장(로그인유저)의 member객체받아오기
        // TODO 팀장의 직책 -> 팀원으로 변경
        // TODO 업데이트해서 정보수정기능으로 접근 lock하기
    }

    // 프로젝트종료버튼
    @Transactional
    public void projectComplete(Long teamId) {
        TeamPost teamPost = teamPostRepository.findById(teamId)
                .orElseThrow(()-> new IllegalIdentifierException("team is not found"));
        teamPost.projectEnd();
    }

    // 팀원평가
    @Transactional
    public void teamEvaluation(Long teamId, Long userId, TeamEvaluationRequestDto evaluationRequestDto) {
        // TODO 팀아이디를 통해 팀원정보리스트업
        // TODO 팀원정보가 authentication과 일치하면 무시
        // TODO 팀원정보 비일치시 팀원정보리스트 for문돌며 평가진행. -> 일괄평가방식

        // TODO userId가 authentiction과 일치시 종료
        TeamPost teamPost = teamPostRepository.findById(teamId)
                .orElseThrow(()-> new IllegalIdentifierException("team is not found"));
        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new IllegalIdentifierException("member is not found"));
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(teamPost,member)
                .orElseThrow(()-> new IllegalIdentifierException("teamMember is not found"));

        // TODO findbyUserID로 profile찾기

        TeamEvaluation teamEvaluation = TeamEvaluation.builder()
                .teamId(teamMember.getTeamId())
                .userId(teamMember.getUserId())
                //.profileId(찾은거입력하기)
                .kindness(evaluationRequestDto.getKindness())
                .promise(evaluationRequestDto.getPromise())
                .frequency(evaluationRequestDto.getFrequency())
                .participate(evaluationRequestDto.getParticipate())
                .total((evaluationRequestDto.getKindness()+
                        evaluationRequestDto.getFrequency()+
                        evaluationRequestDto.getFrequency()+
                        evaluationRequestDto.getParticipate())/4.0)
                .build();
        teamEvaluationRepository.save(teamEvaluation);

        // TODO teamMember의 iscomplete 객체 추가하고 true로 변경(기본false)
    }
}
