package letsit_backend.service;

import letsit_backend.dto.ApplicantProfileDto;
import letsit_backend.model.Apply;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.model.Profile;
import letsit_backend.repository.ApplyRepository;
import letsit_backend.repository.PostRepository;
import letsit_backend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplyService {
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final ProfileRepository profileRepository;

    // 지원자 목록(프사, 닉넴) 리스트업
    @Transactional(readOnly = true)
    public List<ApplicantProfileDto> getApplicantProfiles(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        List<Apply> applies = applyRepository.findByPostId(post);

        log.info("Applicants for post Id : {}", postId);
        return applies.stream()
                .map(apply-> {
                    Member member = apply.getUserId();
                    Profile profile = profileRepository.findByUserId(member);
                    return ApplicantProfileDto.fromEntity(profile);
                })
                .collect(Collectors.toList());
    }
    // 특정 지원자 승인 로직

    // 특정 지원자 거절 로직
}
