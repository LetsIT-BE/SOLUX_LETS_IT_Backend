package letsit_backend.service;

import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostRequestDto.ProjectInfo;
import letsit_backend.dto.PostRequestDto.RecruitPeriod;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.model.Post;
import letsit_backend.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        Post post = Post.builder()
                .postId(1L)
                .title("Test Title")
                .content("Test Content")
                .peopleNum(5)
                .recruitPeriodStart(Timestamp.valueOf("2024-07-01 00:00:00"))
                .recruitPeriodEnd(Timestamp.valueOf("2024-07-31 23:59:59"))
                .projectPeriodStart(Timestamp.valueOf("2024-08-01 00:00:00"))
                .projectPeriodEnd(Timestamp.valueOf("2024-10-31 23:59:59"))
                .difficulty(Post.Difficulty.basic)
                .onOff(true)
//                .region(null) // 설정 필요 시 설정
                .categoryId(1L)
                .viewCount(0)
                .scrapCount(0)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .deadline(false)
                .stack(List.of("Java", "Spring"))
                .preference("Test Preference")
                .ageGroup(Post.AgeGroup.s20b)
                .build();

        Mockito.when(postRepository.save(any(Post.class))).thenReturn(post);
    }

    @Test
    public void testCreatePost() {
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setTitle("Test Title");
        postRequestDto.setContent("Test Content");
        postRequestDto.setPeopleNum(5);

        RecruitPeriod recruitPeriod = new RecruitPeriod();
        recruitPeriod.setStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
        recruitPeriod.setEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
        postRequestDto.setRecruitPeriod(recruitPeriod);

        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectPeriodStart(Timestamp.valueOf("2024-08-01 00:00:00"));
        projectInfo.setProjectPeriodEnd(Timestamp.valueOf("2024-10-31 23:59:59"));
        projectInfo.setAgeGroup(Post.AgeGroup.s20b);
        postRequestDto.setProjectInfo(projectInfo);

        postRequestDto.setDifficulty(Post.Difficulty.basic);
        postRequestDto.setOnOff(true);
//        postRequestDto.setRegionId(null); // 설정 필요 시 설정
//        postRequestDto.setCategoryId(1L);
        postRequestDto.setStack(List.of("Java", "Spring"));
        postRequestDto.setPreference("Test Preference");

        PostResponseDto postResponseDto = postService.createPost(postRequestDto);

        assertThat(postResponseDto).isNotNull();
        assertThat(postResponseDto.getTitle()).isEqualTo("Test Title");
        assertThat(postResponseDto.getContent()).isEqualTo("Test Content");
        assertThat(postResponseDto.getPeopleNum()).isEqualTo(5);
        assertThat(postResponseDto.getRecruitPeriod().getStartDate()).isEqualTo(Timestamp.valueOf("2024-07-01 00:00:00"));
        assertThat(postResponseDto.getRecruitPeriod().getEndDate()).isEqualTo(Timestamp.valueOf("2024-07-31 23:59:59"));
        assertThat(postResponseDto.getProjectInfo().getProjectPeriodStart()).isEqualTo(Timestamp.valueOf("2024-08-01 00:00:00"));
        assertThat(postResponseDto.getProjectInfo().getProjectPeriodEnd()).isEqualTo(Timestamp.valueOf("2024-10-31 23:59:59"));
        assertThat(postResponseDto.getDifficulty()).isEqualTo(Post.Difficulty.basic);
        assertThat(postResponseDto.getOnOff()).isTrue();
        assertThat(postResponseDto.getCategoryId()).isEqualTo(1L);
        assertThat(postResponseDto.getStack()).containsExactly("Java", "Spring");
        assertThat(postResponseDto.getPreference()).isEqualTo("Test Preference");
        assertThat(postResponseDto.getAgeGroup()).isEqualTo(Post.AgeGroup.s20b);
    }
}