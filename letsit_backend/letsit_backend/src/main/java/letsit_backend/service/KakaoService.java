package letsit_backend.service;

/*
import ch.qos.logback.core.spi.ErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
//import letsit_backend.dao.MemberDao;
import letsit_backend.dto.KakaoTokenDto;
import letsit_backend.dto.KakaoMemberDto;
import letsit_backend.dto.LoginResponseDto;
import letsit_backend.jwt.CustomException;
import letsit_backend.jwt.JwtProvider;
import letsit_backend.model.KakaoProfile;
import letsit_backend.model.Member;
import letsit_backend.model.Role;
import letsit_backend.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.*;
//import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import letsit_backend.dto.KakaoTokenDto;
import letsit_backend.dto.LoginResponseDto;
import letsit_backend.jwt.CustomException;
import letsit_backend.jwt.JwtProvider;
import letsit_backend.model.KakaoProfile;
import letsit_backend.model.Member;
import letsit_backend.model.Role;
import letsit_backend.repository.MemberRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Data
@Service
public class KakaoService {


    //private final MemberDao memberDao;
    private final JwtProvider jwtProvider;
    private MemberRepository memberRepository;

    @Autowired
    public KakaoService( JwtProvider jwtProvider, MemberRepository memberRepository) {
        //this.memberDao = memberDao;
        this.jwtProvider = jwtProvider;
        this.memberRepository = memberRepository;
    }

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    //컨트롤러의 kakaoservice.getkakaoaccesstoken(code)처리
    @Transactional
    public KakaoTokenDto getKakaoToken(String code) {


        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");



        //http response body 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        //git ignore 해야
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);


        //프론트에서 인가 코드 요청시 받은 인가 코드 값
        params.add("code", code);

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
            log.error("Error parsing Kakao token response", e);
            //e.printStackTrace();
        }
        return kakaoTokenDto;



    }


    //컨트롤러의 return authservice.kakaologin(kakaotoken)처
    public ResponseEntity<LoginResponseDto> kakaoLogin(String kakaoToken) {
        log.info("Received Kakao token: {}", kakaoToken);

        KakaoProfile kakaoProfile = findProfile(kakaoToken);


        if (kakaoProfile == null || kakaoProfile.getKakao_account() == null) {
            // KakaoProfile 또는 KakaoAccount가 null인 경우 예외 처리
            log.error("Kakao profile or account is null");

            return ResponseEntity.badRequest().build(); // 예시로 bad request 반환
        }

        String username = String.valueOf(kakaoProfile.getId());

        log.info("username set to : {}", username);

        Member member = Member.builder()
                .kakaoId(kakaoProfile.getId())
                .username(username)
                .name(kakaoProfile.getKakao_account().getName())
                .age_range(kakaoProfile.getKakao_account().getAge_range())
                .gender(kakaoProfile.getKakao_account().getGender())
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
        //System.out.println("kakaoToken = " + kakaoToken);

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
        //System.out.println("accountInfoResponse = " + accountInfoResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KakaoProfile kakaoProfile = null;
        try {
            //log.info("Parsing Kakao profile response");

            kakaoProfile = objectMapper.readValue(accountInfoResponse.getBody(), KakaoProfile.class);
            //log.info("Parsed Kakao profile: {}", kakaoProfile);

        } catch (JsonProcessingException e) {
            log.error("Error parsing Kakao profile response", e);
            //e.printStackTrace();
        }
        return kakaoProfile;
        /*
        if (kakaoProfile == null) {
            log.error("Kakao profile is null");
        } else if (kakaoProfile.getKakao_account() == null) {
            log.error("Kakao account is null");
        }
        */


        //return kakaoProfile;




    }
    public boolean kakaoLogout(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                entity,
                String.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Successfully logged out from Kakao");
            return true;
        } else {
            log.error("Failed to logout from Kakao: " + response.getBody());
            return false;
        }

    }

    public String getAccessToken(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username" + username));
        return member.getKakaoAccessToken();
    }


    public String getMemberByLogin(Member member) throws CustomException {

        Member loginMember = memberRepository.findByKakaoId(member.getKakaoId())
                .orElseThrow(()-> new CustomException("Invalid login information"));
        return jwtProvider.createToken(loginMember);

    }



}

