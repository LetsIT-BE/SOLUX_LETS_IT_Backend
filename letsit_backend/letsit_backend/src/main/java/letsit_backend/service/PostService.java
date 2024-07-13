package letsit_backend.service;

import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.model.Post;
import letsit_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .peopleNum(requestDto.getPeopleNum())
                .recruitPeriodStart(requestDto.getRecruitPeriod().getStartDate())
                .recruitPeriodEnd(requestDto.getRecruitPeriod().getEndDate())
                .projectPeriod(requestDto.getProjectInfo().getProjectPeriod())
                .difficulty(requestDto.getDifficulty())
                .onOff(requestDto.getOnOff())
                .regionId(requestDto.getRegionId()) // 추가
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

        PostResponseDto responseDto = new PostResponseDto();
        responseDto.setPostId(savedPost.getPostId());
        responseDto.setTitle(savedPost.getTitle());
        responseDto.setContent(savedPost.getContent());
        responseDto.setPeopleNum(savedPost.getPeopleNum());
        responseDto.setRecruitPeriod(new PostResponseDto.RecruitPeriod(savedPost.getRecruitPeriodStart(), savedPost.getRecruitPeriodEnd()));
        responseDto.setPreference(savedPost.getPreference());
        responseDto.setProjectInfo(new PostResponseDto.ProjectInfo(savedPost.getRegionId().toString(), savedPost.getProjectPeriod(), savedPost.getAgeGroup()));
        responseDto.setStack(savedPost.getStack());
        responseDto.setDifficulty(savedPost.getDifficulty());
        responseDto.setOnOff(savedPost.getOnOff());
        responseDto.setCategoryId(savedPost.getCategoryId());
        responseDto.setAgeGroup(savedPost.getAgeGroup());
        responseDto.setCreatedAt(savedPost.getCreatedAt());
        responseDto.setUpdatedAt(savedPost.getUpdatedAt());

        return responseDto;
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

    public PostResponseDto getPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostResponseDto responseDto = new PostResponseDto();
            responseDto.setPostId(post.getPostId());
            responseDto.setTitle(post.getTitle());
            responseDto.setContent(post.getContent());
            responseDto.setPeopleNum(post.getPeopleNum());
            responseDto.setRecruitPeriod(new PostResponseDto.RecruitPeriod(post.getRecruitPeriodStart(), post.getRecruitPeriodEnd()));
            responseDto.setPreference(post.getPreference());
            responseDto.setProjectInfo(new PostResponseDto.ProjectInfo(post.getRegionId().toString(), post.getProjectPeriod(), post.getAgeGroup()));
            responseDto.setStack(post.getStack());
            responseDto.setDifficulty(post.getDifficulty());
            responseDto.setOnOff(post.getOnOff());
            responseDto.setCategoryId(post.getCategoryId());
            responseDto.setAgeGroup(post.getAgeGroup());
            responseDto.setCreatedAt(post.getCreatedAt());
            responseDto.setUpdatedAt(post.getUpdatedAt());
            responseDto.setViewCount(post.getViewCount());
            responseDto.setScrapCount(post.getScrapCount());
            return responseDto;
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
}
