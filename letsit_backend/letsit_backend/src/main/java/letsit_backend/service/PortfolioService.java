package letsit_backend.service;


import letsit_backend.dto.portfolio.DailyPortfolioListDto;
import letsit_backend.dto.portfolio.DailyPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioResponseDto;
import letsit_backend.model.Member;
import letsit_backend.model.ProjectPortfolio;
import letsit_backend.model.TeamPost;
import letsit_backend.repository.MemberRepository;
import letsit_backend.repository.PortfolioRepository;
import letsit_backend.repository.TeamPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final TeamPostRepository teamPostRepository;
    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;

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
                        p.getPrtId(),
                        p.getTeamId().getTeamId(),
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
}
