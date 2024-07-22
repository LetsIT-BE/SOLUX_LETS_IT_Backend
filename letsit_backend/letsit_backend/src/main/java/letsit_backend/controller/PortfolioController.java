package letsit_backend.controller;

import letsit_backend.dto.Response;
import letsit_backend.dto.portfolio.DailyPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioResponseDto;
import letsit_backend.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;

    // TODO 회원 확인 로직 구현 후 {userId} 관련 부분 삭제
    @PostMapping(value = "/{teamId}/{userId}/write")
    public Response<DailyPortfolioResponseDto> post(@PathVariable Long teamId, @PathVariable Long userId, @RequestBody DailyPortfolioRequestDto request) {
        DailyPortfolioResponseDto newPrt = portfolioService.create(teamId, userId, request);
        return Response.success("성공", newPrt);
    }

    @GetMapping(value = "/{teamId}/list/{userId}")
    public Response<List<DailyPortfolioResponseDto>> getPrtList(@PathVariable Long teamId, @PathVariable Long userId) {
        List<DailyPortfolioResponseDto> prtList = portfolioService.getPrtList(teamId, userId);
        return Response.success("포트폴리오 리스트", prtList);
    }
}
