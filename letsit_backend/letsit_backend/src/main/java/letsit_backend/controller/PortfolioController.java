package letsit_backend.controller;

import letsit_backend.dto.Response;
import letsit_backend.dto.portfolio.AiPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioListDto;
import letsit_backend.dto.portfolio.DailyPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioResponseDto;
import letsit_backend.model.Member;
import letsit_backend.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;

    // TODO 회원 확인 로직 구현 후 {userId} 관련 부분 삭제
    @PostMapping(value = "/{teamId}/{userId}/write")
    public Response<DailyPortfolioResponseDto> post(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId, @RequestBody DailyPortfolioRequestDto request) {
        DailyPortfolioResponseDto newPrt = portfolioService.create(teamId, userId, request);
        return Response.success("성공", newPrt);
    }

    @GetMapping(value = "/{teamId}/list/{userId}")
    public Response<List<DailyPortfolioListDto>> getPrtList(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {
        List<DailyPortfolioListDto> prtList = portfolioService.getPrtList(teamId, userId);
        return Response.success("포트폴리오 리스트", prtList);
    }

    @GetMapping(value = "/{teamId}/details/{prtId}")
    public Response<DailyPortfolioResponseDto> getPrt(@PathVariable("prtId") Long prtId) {
        DailyPortfolioResponseDto prt = portfolioService.read(prtId);
        return Response.success("포트폴리오 상세조회", prt);
    }

    @GetMapping("/{teamId}/aiprt/{userId}")
    public Response<String> getAiPrt(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {
        String response = String.valueOf(portfolioService.aiPrt(teamId, userId).getOutput());
        return Response.success("AI Portfolio Generated: ", response);
    }

    @DeleteMapping("/{prtId}/delete")
    public Response<String> deletePrt(@PathVariable Long prtId) {
        portfolioService.delete(prtId);
        return Response.success("지원서를 삭제하였습니다", null);
    }
}
