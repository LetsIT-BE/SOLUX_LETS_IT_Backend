package letsit_backend.service;

import letsit_backend.dto.profile.ProfileDto;
import letsit_backend.dto.profile.ProfileRequestDto;
import letsit_backend.model.Member;
import letsit_backend.model.Profile;
import letsit_backend.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfileById(long id) {
        return profileRepository.findById(id).orElse(null);
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public void deleteProfileById(long id) {
        profileRepository.deleteById(id);
    }

    public void createOrUpdateProfile(ProfileRequestDto profileDto) {
        Member member = new Member();
        member.setUserId(profileDto.getUserId());
        Profile profile = profileRepository.findByUserId(member);
        if (profile == null) {
            profile = new Profile();
            profile.setUserId(member);
        }
        profile.setNickname(profileDto.getNickname());
        profile.setAge(profileDto.getAge());
        profileRepository.save(profile);
    }

    public void updateProfile(ProfileDto profileDto) {
        Member member = new Member();
        member.setUserId(profileDto.getUserId());
        Profile profile = profileRepository.findByUserId(member);
        if (profile == null) {
            throw new IllegalArgumentException("프로필이 존재하지 않습니다.");
        }
        if (profileDto.getMannerTier() != null) {
            profile.setMannerTier(profileDto.getMannerTier());
        }
        if (profileDto.getMannerScore() != 0) {
            profile.setMannerScore(profileDto.getMannerScore());
        }
        if (profileDto.getSns() != null) {
            profile.setSns(profileDto.getSns());
        }
        if (profileDto.getProfileImageUrl() != null) {
            profile.setProfileImageUrl(profileDto.getProfileImageUrl());
        }
        if (profileDto.getBio() != null) {
            profile.setBio(profileDto.getBio());
        }
        if (profileDto.getSelfIntro() != null) {
            profile.setSelfIntro(profileDto.getSelfIntro());
        }
        if (profileDto.getSkills() != null) {
            profile.setSkills(profileDto.getSkills());
        }
        profileRepository.save(profile);
    }
    public void updateMannerTier(Member userId) {
        Profile profile = profileRepository.findByUserId(userId);
        profile.updateMannerTier();

        profileRepository.save(profile);
    }
}
