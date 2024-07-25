package letsit_backend.controller;


import letsit_backend.dto.profile.ProfileDto;
import letsit_backend.model.Member;
import letsit_backend.model.Profile;
import letsit_backend.service.MemberService;
import letsit_backend.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        logger.debug("getAllProfiles called");
        return profileService.getAllProfiles();
    }

    @GetMapping("/{userId}")
    public Profile getProfileById(@PathVariable Long userId) {
        logger.debug("getProfileById called with userId: {}", userId);
        return profileService.getProfileById(userId);
    }

    @PostMapping
    public Profile createProfile(@RequestBody ProfileDto profileDto) {
        logger.debug("createProfile called with profile: {}", profileDto);
        Long userId = profileDto.getUserId();
        Member member = memberService.getMemberById(profileDto.getUserId());
        logger.debug("createProfile called with profile: {}", profileDto);
        if (member == null) {
            throw new IllegalArgumentException("Invalid userId " + profileDto.getUserId());
        }

        Profile profile = convertToEntity(profileDto);
        profile.setUserId(member);
        return profileService.saveProfile(profile);
    }

    @PatchMapping("/{userId}")
    public Profile updateProfile(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        logger.debug("updateProfile called with userId: {} and profile: {}", userId, profileDto);

        Member member= memberService.getMemberById(profileDto.getUserId());
        if (member == null) {
            throw new IllegalArgumentException("Invalid userId" + profileDto.getUserId());
        }

        Profile profile = convertToEntity(profileDto);
        profile.setProfileId(userId);
        profile.setUserId(member);
        return profileService.saveProfile(profile);
    }

    @DeleteMapping("/{userId}")
    public void deleteProfile(@PathVariable Long userId) {
        logger.debug("deleteProfile called with userId: {}", userId);
        profileService.deleteProfileById(userId);
    }

    private Profile convertToEntity(ProfileDto profileDto) {
        Member member = memberService.getMemberById(profileDto.getUserId());
        return Profile.builder()
                .profileId(profileDto.getProfileId())
                .userId(member)
                .mannerTier(profileDto.getMannerTier())
                .mannerScore(profileDto.getMannerScore())
                .nickname(profileDto.getNickname())
                .age(profileDto.getAge())
                .sns(profileDto.getSns())
                .profile_image_url(profileDto.getProfile_image_url())
                .bio(profileDto.getBio())
                .selfIntro(profileDto.getSelfIntro())
                .skills(profileDto.getSkills())
                .build();
    }

}
