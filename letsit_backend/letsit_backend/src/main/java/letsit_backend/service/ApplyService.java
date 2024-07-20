package letsit_backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letsit_backend.dto.ApplicantProfileDto;
import letsit_backend.dto.ApplyRequestDto;
import letsit_backend.dto.ApplyResponseDto;
import letsit_backend.model.Apply;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.model.Profile;
import letsit_backend.repository.ApplyRepository;
import letsit_backend.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;
    private final ProfileRepository profileRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public ApplyResponseDto create(Long postId, Long userId, ApplyRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(""));
        List<Apply> applies = applyRepository.findByPostId(post);
        // 이미 지원했는지 찾아보고
        boolean alreadyApplied = applies.stream()
                .anyMatch(apply -> apply.getUserId().getUserId().equals(request.getUserId()));

        if (alreadyApplied) {
            throw new IllegalArgumentException("이미 지원한 게시글입니다.");
        }
        Apply apply = request.toEntity(post, member);
        Apply submittedApply = applyRepository.save(apply);

        return new ApplyResponseDto(submittedApply);
    }

    public ApplyResponseDto read(Long applyId) {
        // TODO 요청한 사람이 지원자 || 게시자 인지 확인
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new IllegalArgumentException("신청서가 존재하지 않습니다."));
        return new ApplyResponseDto(apply);
    }

    public void delete(Long applyId) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new IllegalArgumentException("신청서가 존재하지 않습니다."));
        applyRepository.delete(apply);
    }


    // 지원자 목록(프사, 닉넴) 리스트업
    @Transactional(readOnly = true)
    public List<ApplicantProfileDto> getApplicantProfiles(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        List<Apply> applies = applyRepository.findByPostId(post);

        log.info("Applicants for post Id : {}", postId);
        return applies.stream()
                .filter(Apply::isNullYet)
                .map(apply-> {
                    Member member = apply.getUserId();
                    Profile profile = profileRepository.findByUserId(member);
                    return ApplicantProfileDto.fromEntity(profile, apply);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApplicantProfileDto> getApprovedApplicantProfiles(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        List<Apply> applies = applyRepository.findByPostId(post);
        return applies.stream()
                .filter(Apply::isApproved)
                .map(apply -> {
                    Member member = apply.getUserId();
                    Profile profile = profileRepository.findByUserId(member);
                    return ApplicantProfileDto.fromEntity(profile, apply);
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
