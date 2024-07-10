package letsit_backend.service;

import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.model.Post;
import letsit_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = Post.builder()
                .peopleNum(requestDto.getPeopleNum())
                .recruitPeriodStart(requestDto.getRecruitPeriod().getStartDate())
                .recruitPeriodEnd(requestDto.getRecruitPeriod().getEndDate())
                .preference(requestDto.getPreference())
                .content(requestDto.getContent())
                .regionId(requestDto.getProjectInfo().getRegionId())
                .projectPeriodStart(requestDto.getProjectInfo().getProjectPeriodStart())
                .projectPeriodEnd(requestDto.getProjectInfo().getProjectPeriodEnd())
                .stack(requestDto.getStack())
                .ageGroup(requestDto.getProjectInfo().getAgeGroup())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();

        Post savedPost = postRepository.save(post);

        PostResponseDto responseDto = new PostResponseDto();
        responseDto.setPostId(savedPost.getPostId());
        responseDto.setPeopleNum(savedPost.getPeopleNum());
        responseDto.setRecruitPeriod(requestDto.getRecruitPeriod());
        responseDto.setPreference(savedPost.getPreference());
        responseDto.setContent(savedPost.getContent());
        responseDto.setProjectInfo(requestDto.getProjectInfo());
        responseDto.setStack(savedPost.getStack());
        responseDto.setCreatedAt(savedPost.getCreatedAt());
        responseDto.setUpdatedAt(savedPost.getUpdatedAt());

        return responseDto;
    }
}
