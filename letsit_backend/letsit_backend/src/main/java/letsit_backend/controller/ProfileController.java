package letsit_backend.controller;

import letsit_backend.dto.profile.ProfileDto;
import letsit_backend.dto.profile.ProfileRequestDto;
import letsit_backend.model.Member;
import letsit_backend.model.Profile;
import letsit_backend.service.MemberService;
import letsit_backend.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Profile> getAllProfiles() {
        logger.debug("모든 프로필 조회 요청");
        return profileService.getAllProfiles();
    }

    @GetMapping("/{userId}")
    public Profile getProfileById(@PathVariable("userId") Long userId) {
        logger.debug("userId로 프로필 조회 요청: {}", userId);
        Profile profile = profileService.getProfileById(userId);
        logger.debug("조회된 프로필: {}", profile);
        return profile;
    }

    @PostMapping
    public Profile createProfile(@RequestBody ProfileDto profileDto) {
        logger.debug("프로필 생성 요청: {}", profileDto);
        Member member = memberService.getMemberById(profileDto.getUserId());
        logger.debug("조회된 회원: {}", member);
        if (member == null) {
            throw new IllegalArgumentException("유효하지 않은 userId " + profileDto.getUserId());
        }

        Profile profile = convertFromDtoToEntity(profileDto);
        profile.setUserId(member);
        Profile savedProfile = profileService.saveProfile(profile);
        logger.debug("생성된 프로필: {}", savedProfile);
        return savedProfile;
    }

    @PatchMapping("/{userId}")
    public Profile updateProfile(@PathVariable("userId") Long userId, @RequestBody ProfileDto profileDto) {
        logger.debug("userId와 profileDto로 프로필 수정 요청: {}와 {}", userId, profileDto);

        Member member = memberService.getMemberById(profileDto.getUserId());
        logger.debug("조회된 회원: {}", member);
        if (member == null) {
            throw new IllegalArgumentException("유효하지 않은 userId " + profileDto.getUserId());
        }

        //Profile profile = convertFromDtoToEntity(profileDto);
        profileDto.setUserId(userId);
        profileService.updateProfile(profileDto);
        Profile updatedProfile = profileService.getProfileById(userId);
        logger.debug("수정된 프로필: {}", updatedProfile);
        return updatedProfile;
    }

    @DeleteMapping("/{userId}")
    public void deleteProfile(@PathVariable("userId") Long userId) {
        logger.debug("userId로 프로필 삭제 요청: {}", userId);
        profileService.deleteProfileById(userId);
        logger.debug("삭제된 프로필 userId: {}", userId);
    }

    private Profile convertFromRequestDtoToEntity(ProfileRequestDto profileRequestDto) {
        Member member = memberService.getMemberById(profileRequestDto.getUserId());
        logger.debug("ProfileRequestDto를 Profile 엔티티로 변환: {}", member);
        return Profile.builder()
                .userId(member)
                .nickname(profileRequestDto.getNickname())
                .age(profileRequestDto.getAge())
                .build();
    }

    private Profile convertFromDtoToEntity(ProfileDto profileDto) {
        Member member = memberService.getMemberById(profileDto.getUserId());
        logger.debug("ProfileDto를 Profile 엔티티로 변환: {}", member);
        return Profile.builder()
                .profileId(profileDto.getProfileId())
                .userId(member)
                .nickname(profileDto.getNickname())
                .age(profileDto.getAge())
                .sns(profileDto.getSns())
                .profileImageUrl(profileDto.getProfileImageUrl())
                .bio(profileDto.getBio())
                .selfIntro(profileDto.getSelfIntro())
                .skills(profileDto.getSkills())
                .build();
    }
    /*
    private Profile convertToEntity(ProfileRequestDto profileDto) {
        Member member = memberService.getMemberById(profileDto.getUserId());
        logger.debug("ProfileDto를 Profile 엔티티로 변환: {}", member);
        return Profile.builder()
                .profileId(profileDto.getProfileId())
                .userId(member)
                .mannerTier(profileDto.getMannerTier())
                .mannerScore(profileDto.getMannerScore())
                .nickname(profileDto.getNickname())
                .age(profileDto.getAge())
                .sns(profileDto.getSns())
                .profileImageUrl(profileDto.getProfileImageUrl())
                .bio(profileDto.getBio())
                .selfIntro(profileDto.getSelfIntro())
                .skills(profileDto.getSkills())
                .build();
    }

     */
}
