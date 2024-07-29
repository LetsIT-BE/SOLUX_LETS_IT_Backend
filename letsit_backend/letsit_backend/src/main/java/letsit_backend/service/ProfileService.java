package letsit_backend.service;

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
        profile.setProfileImageUrl(profileDto.getProfileImageUrl());
        profile.setBio(profileDto.getBio());
        profile.setSelfIntro(profileDto.getSelfIntro());
        profileRepository.save(profile);
    }

}
