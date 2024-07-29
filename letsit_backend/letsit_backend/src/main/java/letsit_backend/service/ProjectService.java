package letsit_backend.service;

import letsit_backend.dto.OngoingProjectDto;
import letsit_backend.dto.ProjectDto;
import letsit_backend.model.Apply;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.model.TeamPost;
import letsit_backend.model.Profile;
import letsit_backend.repository.*;
//import letsit_backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TeamPostRepository teamPostRepository;
    private final ApplyRepository applyRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ProfileRepository profileRepository;


    @Autowired
    public ProjectService(PostRepository postRepository, MemberRepository memberRepository, TeamPostRepository teamPostRepository, ApplyRepository applyRepository, TeamMemberRepository teamMemberRepository, ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.teamPostRepository = teamPostRepository;
        this.applyRepository = applyRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.profileRepository = profileRepository;
    }

    public List<ProjectDto> getProjectsByUserId(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        List<Post> posts = postRepository.findByUserIdAndDeadlineFalse(user);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<ProjectDto> getAppliedProjectsByUserId(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        List<Apply> applies = applyRepository.findByUserId(user);
        return applies.stream()
                .map(apply -> convertToDto(apply.getPostId()))
                .collect(Collectors.toList());
    }

    public List<OngoingProjectDto> getOngoingProjectsByUserId(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        List<TeamPost> teamPosts = teamPostRepository.findByUser_UserIdAndIsCompleteFalse(userId);
        return teamPosts.stream()
                .map(this::convertToOngoingProjectDto)
                .collect(Collectors.toList());
    }

    public List<OngoingProjectDto> getCompletedProjectsByUserId(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        List<TeamPost> teamPosts = teamPostRepository.findByUser_UserIdAndIsCompleteTrue(userId);
        return teamPosts.stream()
                .map(this::convertToOngoingProjectDto)
                .collect(Collectors.toList());
    }


//    private OngoingProjectDto convertToOngoingProjectDto(TeamPost teamPost) {
//        return new OngoingProjectDto(teamPost.getTeamId(), teamPost.getPrjTitle());
//    }
    private OngoingProjectDto convertToOngoingProjectDto(TeamPost teamPost) {
        List<String> profileImages = teamMemberRepository.findByTeamId_TeamId(teamPost.getTeamId()).stream()
            .map(teamMember -> {
                Profile profile = profileRepository.findByUserId(teamMember.getUserId());
                return profile != null ? profile.getProfileImage() : null;
            })
            .collect(Collectors.toList());
        return new OngoingProjectDto(teamPost.getTeamId(), teamPost.getPrjTitle(), profileImages);
    }


    private ProjectDto convertToDto(Post post) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setPostId(post.getPostId());
        projectDto.setPrtTitle(post.getTitle());
        projectDto.setRegionId(post.getRegion().getName());
        projectDto.setSubRegionId(post.getSubRegion().getName());
        projectDto.setOnoff(post.getOnOff().getKorean());
        projectDto.setStack(post.getStack());
        projectDto.setDifficulty(post.getDifficulty().getKorean());
        projectDto.setUserId(post.getUserId().getUserId());
//        projectDto.setContent(post.getContent());
//        projectDto.setTotalPersonnel(post.getTotalPersonnel());
//        projectDto.setRecruitDueDate(post.getRecruitDueDate().toString());
        projectDto.setProjectPeriod(post.getProjectPeriod().getKorean());
//        projectDto.setDeadline(post.getDeadline());
//        projectDto.setCurrentPersonnel(post.getCurrentPersonnel());
        return projectDto;
    }
}

