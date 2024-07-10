package letsit_backend.service;

import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.model.Post;
import letsit_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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
                .projectPeriodStart(requestDto.getProjectInfo().getProjectPeriodStart())
                .projectPeriodEnd(requestDto.getProjectInfo().getProjectPeriodEnd())
                .difficulty(requestDto.getDifficulty())
                .onOff(requestDto.getOnOff())
//                .region(null)  // 설정 필요 시 설정
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
        responseDto.setRecruitPeriod(requestDto.getRecruitPeriod());
        responseDto.setPreference(savedPost.getPreference());
        responseDto.setProjectInfo(requestDto.getProjectInfo());
        responseDto.setStack(savedPost.getStack());
        responseDto.setDifficulty(savedPost.getDifficulty());
        responseDto.setOnOff(savedPost.getOnOff());
        responseDto.setCategoryId(savedPost.getCategoryId());
        responseDto.setAgeGroup(savedPost.getAgeGroup());
        responseDto.setCreatedAt(savedPost.getCreatedAt());
        responseDto.setUpdatedAt(savedPost.getUpdatedAt());

        return responseDto;
    }
}
