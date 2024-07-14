package letsit_backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;
    /*
    public Apply create(Long postId, ApplyRequestDto request) {
        // 이미 지원했는지 찾아보고


     */

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
    @Transactional
    public void approveApplicant(Long postId, Long applyId) {
        log.info("Approving application. Post ID: {}, Apply ID: {}", postId, applyId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 구인글이 존재하지 않습니다."));
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new IllegalArgumentException("해당 지원서가 존재하지 않습니다."));
        post.approval(apply);

        applyRepository.save(apply);
        log.info("Application approved. Apply ID: {}", applyId);
    }
    // 특정 지원자 거절 로직
    public void rejectApplicant(Long postId, Long applyId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 구인글이 존재하지 않습니다."));
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new IllegalArgumentException("해당 지원서가 존재하지 않습니다."));
        post.reject(apply);

        applyRepository.save(apply);
    }
}
