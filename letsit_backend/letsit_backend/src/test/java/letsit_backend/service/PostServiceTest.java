package letsit_backend.service;

import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.repository.MemberRepository;
import letsit_backend.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private PostRequestDto postRequestDto;
    private Long userId;

    @BeforeEach
    public void setUp() {
        // Create a test member
        Member member = Member.builder()
                .loginId("testuser")
                .password("password")
                .email("testuser@example.com")
                .name("Test User")
                .birth("1990-01-01")
                .gender("M")
                .build();
        Member savedMember = memberRepository.save(member);
        userId = savedMember.getUserId();

        // Setup the post request DTO
        postRequestDto = new PostRequestDto();
        postRequestDto.setTitle("Test Title");
        postRequestDto.setContent("Test Content");
        postRequestDto.setPeopleNum(5);

        PostRequestDto.RecruitPeriod recruitPeriod = new PostRequestDto.RecruitPeriod();
        recruitPeriod.setStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
        recruitPeriod.setEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
        postRequestDto.setRecruitPeriod(recruitPeriod);

        postRequestDto.setPreference("관련 경력 3년 이상, Git 사용 경험");

        PostRequestDto.ProjectInfo projectInfo = new PostRequestDto.ProjectInfo();
        projectInfo.setProjectPeriod(Post.projectPeriod.threeMonths);
        projectInfo.setAgeGroup(Post.AgeGroup.s20c);
        postRequestDto.setProjectInfo(projectInfo);

        postRequestDto.setStack(List.of("Java", "React"));
        postRequestDto.setDifficulty(Post.Difficulty.basic);
        postRequestDto.setOnOff(true);
        postRequestDto.setRegionId(1L);
        postRequestDto.setCategoryId(1L);
    }

    @Test
    public void testCreatePost() {
        PostResponseDto responseDto = postService.createPost(postRequestDto);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getTitle()).isEqualTo("Test Title");
        assertThat(responseDto.getContent()).isEqualTo("Test Content");
        assertThat(responseDto.getPeopleNum()).isEqualTo(5);
    }

    @Test
    public void testDeletePost() {
        PostResponseDto responseDto = postService.createPost(postRequestDto);
        boolean isDeleted = postService.deletePost(userId, responseDto.getPostId());

        assertThat(isDeleted).isTrue();
    }

    @Test
    public void testGetPostById() {
        PostResponseDto createdPost = postService.createPost(postRequestDto);
        PostResponseDto fetchedPost = postService.getPostById(createdPost.getPostId());

        assertThat(fetchedPost).isNotNull();
        assertThat(fetchedPost.getTitle()).isEqualTo("Test Title");
        assertThat(fetchedPost.getContent()).isEqualTo("Test Content");
        assertThat(fetchedPost.getPeopleNum()).isEqualTo(5);
    }

    @Test
    public void testClosePost() {
        PostResponseDto createdPost = postService.createPost(postRequestDto);
        boolean isClosed = postService.closePost(createdPost.getPostId());

        assertThat(isClosed).isTrue();
        PostResponseDto fetchedPost = postService.getPostById(createdPost.getPostId());
        assertThat(fetchedPost.getDeadline()).isTrue();
    }
}
