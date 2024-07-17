package letsit_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import letsit_backend.dto.KakaoMemberDto;
import letsit_backend.dto.KakaoTokenDto;
import letsit_backend.dto.LoginResponseDto;
import letsit_backend.model.KakaoProfile;
import letsit_backend.model.Member;
import letsit_backend.repository.MemberRepository;
import letsit_backend.service.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

//import java.net.http.HttpHeaders;
@Slf4j
@RestController
//@RequestMapping("/api")
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;
    @Autowired
    private MemberRepository memberRepository;

    //프론트에서 인가 코드 받아오는 url
    @GetMapping("/login/oauth2/callback/kakao")
    public ResponseEntity<LoginResponseDto> KakaoLogin(@RequestParam("code") String code) {

        //String code = request.getParameter("code");
        log.info("received auth code {}", code);
        System.out.println("code = " + code);
        //String KakaoToken = kakaoService

        KakaoTokenDto kakaoTokenDto = kakaoService.getKakaoToken(code);
        String kakaoToken = kakaoTokenDto.getAccess_token();

        return kakaoService.kakaoLogin(kakaoToken);
    }
        /*
        if (code == null || code.isEmpty()) {
            log.error("received auth code is null");
            return ResponseEntity.badRequest().build();
        }
        KakaoTokenDto kakaoTokenDto = kakaoService.getKakaoToken(code);
        String kakaoToken = kakaoTokenDto.getAccess_token();
        log.debug("received access token {}", kakaoToken);
        return kakaoService.kakaoLogin(kakaoToken);
    }

         */

    /*
    public Member getLogin(@RequestParam("code") String code) {

        //넘어온 인가 코드로 accesstoken 발급
        KakaoTokenDto kakaoTokenDto = kakaoService.getKakaoToken(code);

        //발급 받은 토큰으로 카카오 회원 DB 저장
        Member member = kakaoService.saveMember(kakaoTokenDto.getAccess_token());


        return member;
    }

    */
    /*
    public KakaoTokenDto getLogin(@RequestParam("code") String code) {

        //넘어온 인가 코드로 accesstoken 발급
        KakaoTokenDto kakaoTokenDto = kakaoService.getKakaoToken(code);
        return kakaoTokenDto;
    }

    */





}
