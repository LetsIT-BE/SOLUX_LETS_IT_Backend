package letsit_backend.service;

import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.model.Area;
import letsit_backend.model.Post;
import letsit_backend.repository.AreaRepository;
import letsit_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AreaRepository areaRepository;

//    public PostResponseDto createPost(PostRequestDto requestDto) {
//        Post post = Post.builder()
//                .title(requestDto.getTitle())
//                .content(requestDto.getContent())
//                .peopleNum(requestDto.getPeopleNum())
//                .recruitPeriodStart(requestDto.getRecruitDueDate().getStartDate())
//                .recruitPeriodEnd(requestDto.getRecruitDueDate().getEndDate())
//                .projectPeriod(requestDto.getProjectInfo().getProjectPeriod())
//                .difficulty(requestDto.getDifficulty())
//                .onOff(requestDto.getOnOff())
//                .regionId(requestDto.getRegionId()) // 추가
//                .categoryId(requestDto.getCategoryId())
//                .viewCount(0)
//                .scrapCount(0)
//                .createdAt(new Timestamp(System.currentTimeMillis()))
//                .updatedAt(new Timestamp(System.currentTimeMillis()))
//                .deadline(false)
//                .stack(requestDto.getStack())
//                .preference(requestDto.getPreference())
//                .ageGroup(requestDto.getProjectInfo().getAgeGroup())
//                .build();
//
//        Post savedPost = postRepository.save(post);
//
//        PostResponseDto responseDto = new PostResponseDto();
//        responseDto.setPostId(savedPost.getPostId());
//        responseDto.setTitle(savedPost.getTitle());
//        responseDto.setContent(savedPost.getContent());
//        responseDto.setPeopleNum(savedPost.getPeopleNum());
//        responseDto.setRecruitDueDate(new PostResponseDto.RecruitDueDate(savedPost.getRecruitPeriodStart(), savedPost.getRecruitPeriodEnd()));
//        responseDto.setPreference(savedPost.getPreference());
//        responseDto.setProjectInfo(new PostResponseDto.ProjectInfo(savedPost.getRegionId().toString(), savedPost.getProjectPeriod(), savedPost.getAgeGroup()));
//        responseDto.setStack(savedPost.getStack());
//        responseDto.setDifficulty(savedPost.getDifficulty());
//        responseDto.setOnOff(savedPost.getOnOff());
//        responseDto.setCategoryId(savedPost.getCategoryId());
//        responseDto.setAgeGroup(savedPost.getAgeGroup());
//        responseDto.setCreatedAt(savedPost.getCreatedAt());
//        responseDto.setUpdatedAt(savedPost.getUpdatedAt());
//
//        return responseDto;
//    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        Area region = areaRepository.findById(requestDto.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid region ID"));

        Area subRegion = areaRepository.findById(requestDto.getSubRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sub-region ID"));
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .peopleNum(requestDto.getPeopleNum())
                .recruitDueDate(requestDto.getRecruitDueDate())
                .projectPeriod(requestDto.getProjectInfo().getProjectPeriod())
                .difficulty(requestDto.getDifficulty())
                .onOff(requestDto.getOnOff())
                .region(region)
                .subRegion(subRegion)
                .categoryId(requestDto.getCategoryId())
                .viewCount(0)
                .scrapCount(0)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .deadline(false)
                .stack(requestDto.getStack())
                .preference(requestDto.getPreference())
                .ageGroup(requestDto.getProjectInfo().getAgeGroup())
                .build();

        Post savedPost = postRepository.save(post);

//        PostResponseDto.RecruitDueDate recruitDueDate = new PostResponseDto.RecruitDueDate(
//                savedPost.getRecruitPeriodStart(),
//                savedPost.getRecruitPeriodEnd()
//        );

        PostResponseDto.ProjectInfo projectInfo = new PostResponseDto.ProjectInfo(
                savedPost.getRegion().getName(),
                savedPost.getSubRegion().getName(),
                savedPost.getProjectPeriod(),
                savedPost.getAgeGroup()
        );

        return new PostResponseDto(
                savedPost.getPostId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getPeopleNum(),
                savedPost.getRecruitDueDate(),
                savedPost.getPreference(),
                projectInfo,
                savedPost.getStack(),
                savedPost.getDifficulty(),
                savedPost.getOnOff(),
                savedPost.getDeadline(),
                savedPost.getCategoryId(),
                savedPost.getAgeGroup(),
                savedPost.getRegion().getName(),
                savedPost.getSubRegion().getName(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt(),
                savedPost.getViewCount(),
                savedPost.getScrapCount()
        );
    }

//    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid postId"));
//
//        post.setPeopleNum(postRequestDto.getPeopleNum());
//        post.setRecruitPeriodStart(postRequestDto.getRecruitDueDate().getStartDate());
//        post.setRecruitPeriodEnd(postRequestDto.getRecruitDueDate().getEndDate());
//        post.setPreference(postRequestDto.getPreference());
////        post.setRegionId(postRequestDto.getProjectInfo().getRegionId());
//        post.setProjectPeriod(postRequestDto.getProjectInfo().getProjectPeriod());
//        post.setAgeGroup(postRequestDto.getProjectInfo().getAgeGroup());
//        post.setStack(postRequestDto.getStack());
//        post.setContent(postRequestDto.getContent());
//        post.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//
//        Post updatedPost = postRepository.save(post);
//
//        return new PostResponseDto(
//                updatedPost.getPostId(),
//                updatedPost.getPeopleNum(),
//                new PostResponseDto.RecruitDueDate(updatedPost.getRecruitPeriodStart(), updatedPost.getRecruitPeriodEnd()),
//                updatedPost.getPreference(),
//                updatedPost.getRegionId(),
//                updatedPost.getProjectPeriod(),
//                updatedPost.getAgeGroup(),
//                updatedPost.getStack(),
//                updatedPost.getContent(),
//                updatedPost.getCreatedAt(),
//                updatedPost.getUpdatedAt()
//        );
//    }

    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postId"));
        Area region = areaRepository.findById(postRequestDto.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid region ID"));

        Area subRegion = areaRepository.findById(postRequestDto.getSubRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sub-region ID"));


        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setPeopleNum(postRequestDto.getPeopleNum());
        post.setRecruitDueDate(postRequestDto.getRecruitDueDate());
        post.setPreference(postRequestDto.getPreference());
        post.setRegion(region);
        post.setSubRegion(subRegion);
        post.setProjectPeriod(postRequestDto.getProjectInfo().getProjectPeriod());
        post.setAgeGroup(postRequestDto.getProjectInfo().getAgeGroup());
        post.setStack(postRequestDto.getStack());
        post.setDifficulty(postRequestDto.getDifficulty());
        post.setOnOff(postRequestDto.getOnOff());
        post.setCategoryId(postRequestDto.getCategoryId());
        post.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Post updatedPost = postRepository.save(post);

//        PostResponseDto.RecruitDueDate recruitDueDate = new PostResponseDto.RecruitDueDate(
//                updatedPost.getRecruitPeriodStart(),
//                updatedPost.getRecruitPeriodEnd()
//        );

        PostResponseDto.ProjectInfo projectInfo = new PostResponseDto.ProjectInfo(
                updatedPost.getRegion().getName(),
                updatedPost.getSubRegion().getName(),
                updatedPost.getProjectPeriod(),
                updatedPost.getAgeGroup()
        );

        return new PostResponseDto(
                updatedPost.getPostId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getPeopleNum(),
                updatedPost.getRecruitDueDate(),
                updatedPost.getPreference(),
                projectInfo,
                updatedPost.getStack(),
                updatedPost.getDifficulty(),
                updatedPost.getOnOff(),
                updatedPost.getDeadline(),
                updatedPost.getCategoryId(),
                updatedPost.getAgeGroup(),
                updatedPost.getRegion().getName(),
                updatedPost.getSubRegion().getName(),
                updatedPost.getCreatedAt(),
                updatedPost.getUpdatedAt(),
                updatedPost.getViewCount(),
                updatedPost.getScrapCount()
        );
    }

//    public boolean deletePost(Long postId) {
//        if (postRepository.existsById(postId)) {
//            postRepository.deleteById(postId);
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean deletePost(Long userId, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getUserId().getUserId().equals(userId)) {
                postRepository.deleteById(postId);
                return true;
            }
        }
        return false;
    }

//    public PostResponseDto getPostById(Long postId) {
//        Optional<Post> optionalPost = postRepository.findById(postId);
//        if (optionalPost.isPresent()) {
//            Post post = optionalPost.get();
//            PostResponseDto responseDto = new PostResponseDto();
//            responseDto.setPostId(post.getPostId());
//            responseDto.setTitle(post.getTitle());
//            responseDto.setContent(post.getContent());
//            responseDto.setPeopleNum(post.getPeopleNum());
//            responseDto.setRecruitDueDate(post.getRecruitDueDate());
//            responseDto.setPreference(post.getPreference());
//            responseDto.setProjectInfo(new PostResponseDto.ProjectInfo(post.getRegionId().toString(), post.getProjectPeriod(), post.getAgeGroup()));
//            responseDto.setStack(post.getStack());
//            responseDto.setDifficulty(post.getDifficulty());
//            responseDto.setOnOff(post.getOnOff());
//            responseDto.setCategoryId(post.getCategoryId());
//            responseDto.setAgeGroup(post.getAgeGroup());
//            responseDto.setCreatedAt(post.getCreatedAt());
//            responseDto.setUpdatedAt(post.getUpdatedAt());
//            responseDto.setViewCount(post.getViewCount());
//            responseDto.setScrapCount(post.getScrapCount());
//            return responseDto;
//        } else {
//            throw new IllegalArgumentException("Invalid post ID");
//        }
//    }

    public PostResponseDto getPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostResponseDto.ProjectInfo projectInfo = new PostResponseDto.ProjectInfo(
                    post.getRegion().getName(),
                    post.getSubRegion().getName(),
                    post.getProjectPeriod(),
                    post.getAgeGroup()
            );
            return new PostResponseDto(
                    post.getPostId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getPeopleNum(),
                    post.getRecruitDueDate(),
                    post.getPreference(),
                    projectInfo,
                    post.getStack(),
                    post.getDifficulty(),
                    post.getOnOff(),
                    post.getDeadline(),
                    post.getCategoryId(),
                    post.getAgeGroup(),
                    post.getRegion().getName(),
                    post.getSubRegion().getName(),
                    post.getCreatedAt(),
                    post.getUpdatedAt(),
                    post.getViewCount(),
                    post.getScrapCount()
            );
        } else {
            throw new IllegalArgumentException("Invalid post ID");
        }
    }

    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }


    public boolean closePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setDeadline(true);
            postRepository.save(post);
            return true;
        } else {
            return false;
        }
    }

    // 게시글 조회
    public List<PostResponseDto> getAllPostsOrderByCreatedAt() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

// 필요없을듯
//    // 스크랩순으로 게시글 조회
//    public List<PostResponseDto> getAllPostsOrderByScrapCount() {
//        List<Post> posts = postRepository.findAllByOrderByScrapCountDesc();
//        return posts.stream().map(this::convertToResponseDto).collect(Collectors.toList());
//    }
//
//    // 조회순으로 게시글 조회
//    public List<PostResponseDto> getAllPostsOrderByViewCount() {
//        List<Post> posts = postRepository.findAllByOrderByViewCountDesc();
//        return posts.stream().map(this::convertToResponseDto).collect(Collectors.toList());
//    }

//    private PostResponseDto convertToResponseDto(Post post) {
//        PostResponseDto responseDto = new PostResponseDto();
//        responseDto.setPostId(post.getPostId());
//        responseDto.setTitle(post.getTitle());
//        responseDto.setContent(post.getContent());
//        responseDto.setPeopleNum(post.getPeopleNum());
//        responseDto.setRecruitDueDate(post.getRecruitDueDate());
//        responseDto.setPreference(post.getPreference());
//        responseDto.setProjectInfo(new PostResponseDto.ProjectInfo(post.getRegionId() != null ? post.getRegionId().toString() : null, post.getProjectPeriod(), post.getAgeGroup()));
//        responseDto.setStack(post.getStack());
//        responseDto.setDifficulty(post.getDifficulty());
//        responseDto.setOnOff(post.getOnOff());
//        responseDto.setCategoryId(post.getCategoryId());
//        responseDto.setAgeGroup(post.getAgeGroup());
//        responseDto.setCreatedAt(post.getCreatedAt());
//        responseDto.setUpdatedAt(post.getUpdatedAt());
//        responseDto.setViewCount(post.getViewCount());
//        responseDto.setScrapCount(post.getScrapCount());
//        return responseDto;
//    }

    private PostResponseDto convertToResponseDto(Post post) {
        PostResponseDto.ProjectInfo projectInfo = new PostResponseDto.ProjectInfo(
                post.getRegion().getName(),
                post.getSubRegion().getName(),
                post.getProjectPeriod(),
                post.getAgeGroup()
        );

        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getPeopleNum(),
                post.getRecruitDueDate(),
                post.getPreference(),
                projectInfo,
                post.getStack(),
                post.getDifficulty(),
                post.getOnOff(),
                post.getDeadline(),
                post.getCategoryId(),
                post.getAgeGroup(),
                post.getRegion().getName(),
                post.getSubRegion().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getViewCount(),
                post.getScrapCount()
        );
    }
}
