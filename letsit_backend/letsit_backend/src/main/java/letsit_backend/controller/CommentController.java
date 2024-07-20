package letsit_backend.controller;

import letsit_backend.dto.comment.CommentRequestDto;
import letsit_backend.dto.comment.CommentResponseDto;
import letsit_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/upload")
    public ResponseEntity<CommentResponseDto> postNewComment(@PathVariable Long postId, @RequestBody CommentRequestDto request) {
        CommentResponseDto savedComment = commentService.upload(postId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedComment);
    }

    @PatchMapping("/{postId}/update/{commentId}/{userId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long userId, @RequestBody CommentRequestDto request) {
        CommentResponseDto updatedComment = commentService.updateComment(postId, commentId, userId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedComment);
    }

    @DeleteMapping("/{postId}/delete/{commentId}/{userId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long userId) {
        commentService.delete(postId, commentId, userId);
        return "댓글을 삭제하였습니다.";
    }
}
