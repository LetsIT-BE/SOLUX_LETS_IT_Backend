package letsit_backend.service;

import letsit_backend.dto.CommentResponseDto;
import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.model.Area;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.repository.AreaRepository;
import letsit_backend.repository.CommentRepository;
import letsit_backend.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public PostResponseDto createPost(PostRequestDto requestDto) {

        Member user = memberRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Area region = areaRepository.findById(requestDto.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid region ID"));

        Area subRegion = areaRepository.findById(requestDto.getSubRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sub-region ID"));

        Post post = Post.builder()
                .userId(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .totalPersonnel(requestDto.getTotalPersonnel())
                .recruitDueDate(requestDto.getRecruitDueDate())
                .projectPeriod(requestDto.getProjectPeriod())
                .difficulty(requestDto.getDifficulty())
                .onOff(requestDto.getOnOff())
                .region(region)
                .subRegion(subRegion)
                .categoryId(String.join(",", requestDto.getCategoryId()))
                .viewCount(0)
                .scrapCount(0)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .deadline(false)
                .stack(String.join(",", requestDto.getStack()))
                .preference(requestDto.getPreference())
                .ageGroup(requestDto.getAgeGroup())
                .build();

        Post savedPost = postRepository.save(post);

        return new PostResponseDto(
                user.getUserId(),
                savedPost.getPostId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getTotalPersonnel(),
                savedPost.getRecruitDueDate(),
                savedPost.getPreference(),
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
                savedPost.getScrapCount(),
                savedPost.getProjectPeriod(),
                null
        );
    }

    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postId"));
        Member user = memberRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Area region = areaRepository.findById(postRequestDto.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid region ID"));

        Area subRegion = areaRepository.findById(postRequestDto.getSubRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sub-region ID"));


        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setTotalPersonnel(postRequestDto.getTotalPersonnel());
        post.setRecruitDueDate(postRequestDto.getRecruitDueDate());
        post.setPreference(postRequestDto.getPreference());
        post.setRegion(region);
        post.setSubRegion(subRegion);
        post.setProjectPeriod(postRequestDto.getProjectPeriod());
        post.setAgeGroup(postRequestDto.getAgeGroup());
        post.setStack(postRequestDto.getStack());
        post.setDifficulty(postRequestDto.getDifficulty());
        post.setOnOff(postRequestDto.getOnOff());
        post.setCategoryId(postRequestDto.getCategoryId());
        post.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Post updatedPost = postRepository.save(post);

        return new PostResponseDto(
                user.getUserId(),
                updatedPost.getPostId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getTotalPersonnel(),
                updatedPost.getRecruitDueDate(),
                updatedPost.getPreference(),
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
                updatedPost.getScrapCount(),
                updatedPost.getProjectPeriod(),
                null
        );
    }

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

    public PostResponseDto getPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            List<CommentResponseDto> comments = commentRepository.findByPostId(post).stream()
                    .map(comment -> new CommentResponseDto(
                            comment.getCommentId(),
                            comment.getUserId().getUserId(),
                            comment.getUserId().getName(),
                            comment.getComContent(),
                            comment.getComCreateDate(),
                            comment.getComUpdateDate()
                    ))
                    .collect(Collectors.toList());

            return new PostResponseDto(
                    post.getUserId().getUserId(),
                    post.getPostId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getTotalPersonnel(),
                    post.getRecruitDueDate(),
                    post.getPreference(),
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
                    post.getScrapCount(),
                    post.getProjectPeriod(),
                    comments
            );
        } else {
            throw new IllegalArgumentException("Invalid post ID");
        }
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

    // 게시글 조회 (마감되지 않은 것만)
    public List<PostResponseDto> getAllPostsByDeadlineFalseOrderByCreatedAt() {
        List<Post> posts = postRepository.findAllByDeadlineFalseOrderByCreatedAtDesc();
        return posts.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    private PostResponseDto convertToResponseDto(Post post) {
        List<CommentResponseDto> comments = commentRepository.findByPostId(post).stream()
                .map(comment -> new CommentResponseDto(
                        comment.getCommentId(),
                        comment.getUserId().getUserId(),
                        comment.getUserId().getName(),
                        comment.getComContent(),
                        comment.getComCreateDate(),
                        comment.getComUpdateDate()
                ))
                .collect(Collectors.toList());

        return new PostResponseDto(
                post.getUserId().getUserId(),
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getTotalPersonnel(),
                post.getRecruitDueDate(),
                post.getPreference(),
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
                post.getScrapCount(),
                post.getProjectPeriod(),
                comments
        );
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

//    private PostResponseDto convertToResponseDto(Post post) {
//        PostResponseDto.ProjectInfo projectInfo = new PostResponseDto.ProjectInfo(
//                post.getRegion().getName(),
//                post.getSubRegion().getName(),
//                post.getProjectPeriod(),
//                post.getAgeGroup()
//        );
//
//        return new PostResponseDto(
//                post.getUserId().getUserId(),
//                post.getPostId(),
//                post.getTitle(),
//                post.getContent(),
//                post.getPeopleNum(),
//                post.getRecruitDueDate(),
//                post.getPreference(),
////                projectInfo,
//                post.getStack(),
//                post.getDifficulty(),
//                post.getOnOff(),
//                post.getDeadline(),
//                post.getCategoryId(),
//                post.getAgeGroup(),
//                post.getRegion().getName(),
//                post.getSubRegion().getName(),
//                post.getCreatedAt(),
//                post.getUpdatedAt(),
//                post.getViewCount(),
//                post.getScrapCount(),
//                post.getProjectPeriod()
//        );
//    }
}
