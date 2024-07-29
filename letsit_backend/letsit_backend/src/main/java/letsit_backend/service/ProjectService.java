//package letsit_backend.service;
//
//import letsit_backend.dto.ProjectDto;
//import letsit_backend.model.Member;
//import letsit_backend.model.Post;
//import letsit_backend.repository.MemberRepository;
//import letsit_backend.repository.PostRepository;
////import letsit_backend.repository.ProjectRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ProjectService {
//    private final PostRepository postRepository;
//    private final MemberRepository memberRepository;
//    private final RegionService regionService;
//
//    @Autowired
//    public ProjectService(PostRepository postRepository, MemberRepository memberRepository, RegionService regionService) {
//        this.postRepository = postRepository;
//        this.memberRepository = memberRepository;
//        this.regionService = regionService;
//    }
//
//    public List<ProjectDto> getProjectsByUserId(Member member) {
//        List<Post> posts = postRepository.findByUserId(member);
//
//        return posts.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private ProjectDto convertToDto(Post post) {
//        ProjectDto projectDto = new ProjectDto();
//        projectDto.setPostId(post.getPostId());
//        projectDto.setPrtTitle(post.getTitle());
//        projectDto.setRegionId(regionService.getRegionNameById(post.getRegionId()));
//        projectDto.setOnoff(post.getOnOff() ? "대면" : "비대면");
////        projectDto.setRequiredStack(post.getStackId());  // Update this based on how you get the stack
//        projectDto.setDifficulty(post.getDifficulty().name());
//        projectDto.setUserId(post.getUserId().getUserId());
//        return projectDto;
//    }
//}