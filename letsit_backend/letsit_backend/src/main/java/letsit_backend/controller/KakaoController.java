package letsit_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import letsit_backend.dto.KakaoMemberDto;
import letsit_backend.dto.KakaoTokenDto;
import letsit_backend.dto.LoginResponseDto;
import letsit_backend.jwt.JwtProvider;
import letsit_backend.model.KakaoProfile;
import letsit_backend.model.Member;
import letsit_backend.repository.MemberRepository;
import letsit_backend.service.KakaoService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//import java.net.http.HttpHeaders;
@Slf4j
@RestController
//@RequestMapping("/api")
public class KakaoController {
    //private static final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private JwtProvider jwtProvider;


    @GetMapping(value = "/user/info")
    public ResponseEntity<?> ask(Authentication authentication) {
        if (authentication == null) {
            log.info("Authentication object is null.");
            return ResponseEntity.status(401).body("Unauthorized");
        }
        // 로그인정보 받아오기
        String loginId = authentication.getName();
        log.info("Logged in user: " + loginId);
        return ResponseEntity.ok("Logged in user: " + loginId);
    }




    @GetMapping("/login/oauth2/callback/kakao")
    public ResponseEntity<Map<String, Object>> KakaoLogin(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        log.info("received auth code {}", code);

        // 인가 코드를 사용하여 카카오 액세스 토큰 얻기
        KakaoTokenDto kakaoTokenDto = kakaoService.getKakaoToken(code);
        String kakaoToken = kakaoTokenDto.getAccess_token();
        log.info("received kakao access token {}", kakaoToken);


        // 카카오 액세스 토큰을 사용하여 사용자 정보 가져오기
        LoginResponseDto loginResponse = kakaoService.kakaoLogin(kakaoToken).getBody();
        log.info("received login response: {}", loginResponse);

        // JWT 토큰 생성
        String jwtToken = null;
        if (loginResponse.isLoginSuccess()) {
            jwtToken = kakaoService.getMemberByLogin(loginResponse.getMember());
            log.info("Generated JWT Token: {}", jwtToken);

            // Set authentication to SecurityContext
            UserDetails userDetails = User.builder()
                    .username(loginResponse.getMember().getName())
                    .password("")
                    .authorities(Collections.emptyList())
                    .build();
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            //response.sendRedirect("/home?token=" + jwtToken);
        } //else {
            //response.sendRedirect("/login?error=invalid_token");

        // 사용자 정보와 JWT 토큰을 응답에 포함
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("user", loginResponse.getMember());
        responseBody.put("token", jwtToken);


        log.info("Returning response to frontend: {}", responseBody);
        //return "redirect:/login/oauth2/callback/kakao";
        return ResponseEntity.ok(responseBody);
        //response.sendRedirect("/?token=" + jwtToken);
        //}
    }


    /*
    @PostMapping("/login/member")
    //@RequestBody Member member 로 프론트로부터 전달된 사용자 정보를 받음
    public ResponseEntity<String> handleLogin(@RequestBody Member member) {
        log.info("-------" + member.getKakaoId() + "님 로그인 시도 -------");
        //System.out.println("member = " + member);

        //kakaoService.getMemberByLogin(member) 호출해서 사용자에 대한 JWT 토큰 생성
        String accessToken = kakaoService.getMemberByLogin(member);
        log.info(member.getKakaoId() + "님에게 발급된 jwt::" + accessToken);

        //HTTP 응답 헤더 생성
        HttpHeaders responseHeaders = new HttpHeaders();
        //응답헤더에 JWT 토큰 설정
        responseHeaders.set("Authorization", "Bearer " + accessToken);
        //responseHeaders.set("accessToken", accessToken);

        //응답 본문 생성

        log.info("------" + member.getKakaoId() + "님 로그인 ------");

        return ResponseEntity.ok().headers(responseHeaders).body("Login successful");
        //return ResponseEntity.ok().headers(responseHeaders).body(accessToken);
        //return ResponseEntity.ok().header("accessToken", accessToken).body(accessToken);
    }


    //@Autowired
    //private MemberRepository memberRepository;

    //프론트에서 인가 코드 받아오는 url
    @GetMapping("/login/oauth2/callback/kakao")
    public ResponseEntity<LoginResponseDto> KakaoLogin(@RequestParam("code") String code) {

        //String code = request.getParameter("code");
        log.info("received auth code {}", code);
        //System.out.println("code = " + code);
        //String KakaoToken = kakaoService

        //메서드 호출해서 카카오 인가 코드로부터 액세스 토큰 받기
        KakaoTokenDto kakaoTokenDto = kakaoService.getKakaoToken(code);
        //액세스 토큰 추출
        String kakaoToken = kakaoTokenDto.getAccess_token();

        //카카오 서버에서 사용자 정보 받아오기
        //받아오 사용자 정보는 LoginResponseDto에 담겨 ResponseEntity<LoginResponseDto>로 반환됨
        ResponseEntity<LoginResponseDto> responseEntity = kakaoService.kakaoLogin(kakaoToken);

        // JWT 토큰을 로그로 출력
        if (responseEntity.getBody().isLoginSuccess()) {
            String jwtToken = kakaoService.getMemberByLogin(responseEntity.getBody().getMember());
            log.info("Generated JWT Token: {}", jwtToken);
        }

        //프론트에게 카카오 로그인 결과와 사용자 정보를 Json 형태로 반환
        return responseEntity;

        //return kakaoService.kakaoLogin(kakaoToken);
    }

     */
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
