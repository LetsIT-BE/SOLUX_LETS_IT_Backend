package letsit_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import letsit_backend.dto.KakaoTokenDto;
import letsit_backend.dto.KakaoMemberDto;
import letsit_backend.dto.LoginResponseDto;
import letsit_backend.model.KakaoProfile;
import letsit_backend.model.Member;
import letsit_backend.model.Role;
import letsit_backend.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

@Slf4j
@Service
public class KakaoService {

    @Autowired
    private MemberRepository memberRepository;

    //컨트롤러의 kakaoservice.getkakaoaccesstoken(code)처리
    @Transactional
    public KakaoTokenDto getKakaoToken(String code) {


        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //http response body 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        //git ignore 해야
        params.add("client_id", "e3e058c59a1767029763d4d598a23ba6");
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/callback/kakao");
        //프론트에서 인가 코드 요청시 받은 인가 코드 값
        params.add("code", code);
        //params.add("client_secret", "0003a8e29eb211a6c4e9ffa967052dd1");

        //http 헤더 바디 합치기 위해 http entity 객체 생성
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //카카오로부터 Access token 받아오기
        RestTemplate restTemplate = new RestTemplate();
        log.info("sending request to kakao: {}",params);
        System.out.println("restTemplate = " + restTemplate);
        
        ResponseEntity<String> kakaoTokenResponse = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        log.info("kakao token response: {}", kakaoTokenResponse.getBody());
        System.out.println("kakaoTokenResponse = " + kakaoTokenResponse);

        
        //JSON parsing -> kakaoTokenDto
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KakaoTokenDto kakaoTokenDto = null;
        try {
            kakaoTokenDto = objectMapper.readValue(kakaoTokenResponse.getBody(), KakaoTokenDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoTokenDto;



    }


    //컨트롤러의 return authservice.kakaologin(kakaotoken)처
    public ResponseEntity<LoginResponseDto> kakaoLogin(String kakaoToken) {
        log.info("Received Kakao token: {}", kakaoToken);
        System.out.println("kakaoToken = " + kakaoToken);

        KakaoProfile kakaoProfile = findProfile(kakaoToken);


        if (kakaoProfile == null || kakaoProfile.getKakao_account() == null) {
            // KakaoProfile 또는 KakaoAccount가 null인 경우 예외 처리
            log.error("Kakao profile or account is null");

            return ResponseEntity.badRequest().build(); // 예시로 bad request 반환
        }



        Member member = Member.builder()
                .kakaoId(kakaoProfile.getId())
                .name(kakaoProfile.kakao_account.getName())
                .age_range(kakaoProfile.kakao_account.getAge_range())
                .gender(kakaoProfile.kakao_account.getGender())
                .profile_image_url(kakaoProfile.getKakao_account().getProfile().getProfile_image_url())
                .role(Role.USER)
                .build();


        LoginResponseDto loginResponseDto = new LoginResponseDto();

        /*
        try {
            Member existOwner = memberRepository.findById(member.getUserId()).orElse(null);

            if (existOwner == null) {
                log.info("First time login for user ID: {}", member.getUserId());
                System.out.println("existOwner = " + existOwner);
                //System.out.println("처음 로그인 하는 회원입니다.");
                memberRepository.save(member);
            }
            loginResponseDto.setLoginSuccess(true);
            loginResponseDto.setMember(member);
            //loginResponseDto.setLoginSuccess(true);

            return ResponseEntity.ok().body(loginResponseDto);

        } catch (Exception e) {
            log.error("Error during Kakao login", e);
            System.out.println("e = " + e);

         */
        try {
            Optional<Member> existOwner = memberRepository.findByKakaoId(member.getKakaoId());

            if (existOwner.isEmpty()) {
                log.info("First time login for user ID: {}", member.getKakaoId());
                memberRepository.save(member);
            } else {
                member = existOwner.get();
            }
            loginResponseDto.setLoginSuccess(true);
            loginResponseDto.setMember(member);

            return ResponseEntity.ok().body(loginResponseDto);

        } catch (Exception e) {
            log.error("Error during Kakao login", e);
            loginResponseDto.setLoginSuccess(false);
            return ResponseEntity.badRequest().body(loginResponseDto);
        }

            //loginResponseDto.setLoginSuccess(false);
            //return ResponseEntity.badRequest().body(loginResponseDto);

    }


/*
    @Transactional
    public Member saveMember(String token) {
        KakaoProfile profile = findProfile(token);

        Optional<Member> existingMemberOptional = memberRepository.findByEmail(profile.getKakao_Account().getEmail());
        Member member = new Member();

        if(member == null) {
            member = Member.builder()
                    .userId(profile.getId())
                    .email(profile.getKakao_Account().getEmail())
                    .picture(profile.getProperties().getProfile_image())
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);
        }
        return member;
    }
*/

    public KakaoProfile findProfile(String kakaoToken) {
        log.info("Fetching Kakao profile with token: {}", kakaoToken);
        System.out.println("kakaoToken = " + kakaoToken);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoToken); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> accountInfoRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> accountInfoResponse = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                accountInfoRequest,
                String.class
        );
        log.info("Kakao profile response: {}", accountInfoResponse.getBody());
        System.out.println("accountInfoResponse = " + accountInfoResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KakaoProfile kakaoProfile = null;
        try {
            log.info("Parsing Kakao profile response");

            kakaoProfile = objectMapper.readValue(accountInfoResponse.getBody(), KakaoProfile.class);
            log.info("Parsed Kakao profile: {}", kakaoProfile);

        } catch (JsonProcessingException e) {
            log.error("Error parsing Kakao profile response", e);
            e.printStackTrace();
        }
        if (kakaoProfile == null) {
            log.error("Kakao profile is null");
        } else if (kakaoProfile.getKakao_account() == null) {
            log.error("Kakao account is null");
        }

        return kakaoProfile;




    }


}

