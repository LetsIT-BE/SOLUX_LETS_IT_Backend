package letsit_backend.controller;

import letsit_backend.CurrentUser;
import letsit_backend.dto.Response;
import letsit_backend.dto.portfolio.DailyPortfolioListDto;
import letsit_backend.dto.portfolio.DailyPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioResponseDto;
import letsit_backend.model.Member;
import letsit_backend.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;

    // TODO {userId} 삭제
    @PostMapping(value = "/{teamId}/write")
    public Response<DailyPortfolioResponseDto> post(@PathVariable Long teamId, @CurrentUser Member member, @RequestBody DailyPortfolioRequestDto request) {
        DailyPortfolioResponseDto newPrt = portfolioService.create(teamId, member, request);
        return Response.success("성공", newPrt);
    }

    // TODO {userId} 삭제
    @GetMapping(value = "/{teamId}/list")
    public Response<List<DailyPortfolioListDto>> getPrtList(@PathVariable Long teamId, @CurrentUser Member member) {
        List<DailyPortfolioListDto> prtList = portfolioService.getPrtList(teamId, member);
        return Response.success("포트폴리오 리스트", prtList);
    }

    // TODO {userId} 삭제
    @GetMapping(value = "/{teamId}/details/{prtId}")
    public Response<DailyPortfolioResponseDto> getPrt(@PathVariable Long prtId, @CurrentUser Member member) {
        DailyPortfolioResponseDto prt = portfolioService.read(prtId, member);
        return Response.success("포트폴리오 상세조회", prt);
    }
}
