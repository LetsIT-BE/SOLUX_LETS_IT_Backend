package letsit_backend.service;


import letsit_backend.dto.portfolio.DailyPortfolioListDto;
import letsit_backend.dto.portfolio.DailyPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioResponseDto;
import letsit_backend.model.Member;
import letsit_backend.model.ProjectPortfolio;
import letsit_backend.model.TeamMember;
import letsit_backend.model.TeamPost;
import letsit_backend.repository.MemberRepository;
import letsit_backend.repository.PortfolioRepository;
import letsit_backend.repository.TeamMemberRepository;
import letsit_backend.repository.TeamPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final TeamPostRepository teamPostRepository;
    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;
    private final TeamMemberRepository teamMemberRepository;

    public DailyPortfolioResponseDto create(Long teamId, Member member, DailyPortfolioRequestDto request) {
        TeamPost team = teamPostRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트입니다."));
        // 보안을 위해 {userId}를 받아야 할까?
        ProjectPortfolio prt = request.toEntity(team, member);
        portfolioRepository.save(prt);

        return new DailyPortfolioResponseDto(prt);
    }


    public List<DailyPortfolioListDto> getPrtList(Long teamId, Member member) {
        TeamPost team = teamPostRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트입니다."));
        List<ProjectPortfolio> prt = portfolioRepository.findAllByTeamIdAndUserId(team, member);

        return prt.stream()
                .sorted((p1, p2) -> p2.getPrtId().compareTo(p1.getPrtId()))
                .map(p -> new DailyPortfolioListDto(
                        p.getTeamId().getTeamId(),
                        p.getPrtId(),
                        p.getPrtTitle(),
                        p.getPrtCreateDate()
                ))
                .collect(Collectors.toList());
    }

    public DailyPortfolioResponseDto read(Long prtId, Member member) {
        ProjectPortfolio prt = portfolioRepository.findById(prtId).orElseThrow(() -> new IllegalArgumentException("신청서가 존재하지 않습니다."));;
        if (!prt.getUserId().equals(member)) {

        }

        return new DailyPortfolioResponseDto(prt);
    }

    public List<Map<Long, String>> totalList(Member member) {
        List<TeamMember> teamMemberList = teamMemberRepository.findAllByUserId(member);

        // Map each team member to a map containing the team ID and project title
        List<Map<Long, String>> teamPostList = teamMemberList.stream()
                .map(teamMember -> {
                    Map<Long, String> map = new HashMap<>();
                    map.put(teamMember.getTeamId().getTeamId(), teamMember.getTeamId().getPrjTitle());
                    return map;
                })
                .collect(Collectors.toList());

        return teamPostList;
    }
}
