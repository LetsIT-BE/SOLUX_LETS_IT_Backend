package letsit_backend.controller;

import jakarta.validation.Valid;
import letsit_backend.dto.MemberResponseDto;
import letsit_backend.dto.MemberSignupRequestDto;
import letsit_backend.dto.Response;
import letsit_backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//@RequiredArg0Constructor
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Response<MemberResponseDto> signup(@Valid @RequestBody MemberSignupRequestDto requestDto) {
        return Response.success("회원 가입 성공", memberService.signUp(requestDto));
    }

    @GetMapping("/test")
    public String test() {
        return "통과";
    }
}
