package letsit_backend.service;

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

    public DailyPortfolioResponseDto create(Long teamId, Long userId, DailyPortfolioRequestDto request) {
        TeamPost team = teamPostRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트입니다."));
        // TODO 회원 확인 로직 구현 후, userId 관련 내용은 삭제
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(""));

        ProjectPortfolio prt = request.toEntity(team, member);
        portfolioRepository.save(prt);

        return new DailyPortfolioResponseDto(prt);
    }

    public List<DailyPortfolioResponseDto> getPrtList(Long teamId, Long userId) {
        TeamPost team = teamPostRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트입니다."));
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(""));
        List<ProjectPortfolio> prt = portfolioRepository.findAllByTeamIdAndUserId(team, member);

        return prt.stream()
                .sorted((p1, p2) -> p2.getPrtId().compareTo(p1.getPrtId()))
                .map(p -> new DailyPortfolioResponseDto(
                        p.getPrtId(),
                        p.getPrtTitle(),
                        p.getPrtCreateDate(),
                        p.getPrtUpdateDate(),
                        "",  // workDescription
                        "",  // issues
                        "",  // solutions
                        ""   // feedback
                ))
                .collect(Collectors.toList());
    }
}
