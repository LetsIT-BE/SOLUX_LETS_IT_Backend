package letsit_backend.service;

import letsit_backend.dto.comment.CommentRequestDto;
import letsit_backend.dto.comment.CommentResponseDto;
import letsit_backend.model.Comment;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.repository.CommentRepository;
import letsit_backend.repository.MemberRepository;
import letsit_backend.repository.PostRepository;
import letsit_backend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    public CommentResponseDto upload(Long postId, Long userId, CommentRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Comment comment = request.toEntity(post, member);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment, profileRepository);
    }

    public CommentResponseDto updateComment(Long postId, Long commentId, Long userId, CommentRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (!comment.getUserId().getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }
        comment.update(request.getComContent());
        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponseDto(updatedComment, profileRepository);
    }

    public void delete(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        commentRepository.delete(comment);
    }
}
