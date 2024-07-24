package letsit_backend.service;

import letsit_backend.dto.OngoingProjectDto;
import letsit_backend.dto.ProjectDto;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.repository.MemberRepository;
import letsit_backend.repository.PostRepository;
//import letsit_backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final RegionService regionService;

    @Autowired
    public ProjectService(PostRepository postRepository, MemberRepository memberRepository, RegionService regionService) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.regionService = regionService;
    }

    public List<ProjectDto> getProjectsByUserId(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        List<Post> posts = postRepository.findByUserId(user);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<OngoingProjectDto> getOngoingProjectsByUserId(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        String status = "진행중"; // 진행 중인 프로젝트 상태
        List<Post> posts = postRepository.findByUserId(user);
        return posts.stream()
                .map(post -> new OngoingProjectDto(post.getTitle()))
                .collect(Collectors.toList());
    }

    private ProjectDto convertToDto(Post post) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setPostId(post.getPostId());
        projectDto.setPrtTitle(post.getTitle());
        projectDto.setRegionId(regionService.getRegionNameById(post.getRegionId()));
        projectDto.setOnoff(post.getOnOff() ? "대면" : "비대면");
//        projectDto.setRequiredStack(post.getStackId());  // Update this based on how you get the stack
        projectDto.setDifficulty(post.getDifficulty().name());
        projectDto.setUserId(post.getUserId().getUserId());
        return projectDto;
    }
}