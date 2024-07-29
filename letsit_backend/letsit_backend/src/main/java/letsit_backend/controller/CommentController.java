package letsit_backend.controller;

import letsit_backend.dto.Response;
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

    @PostMapping("/{postId}/upload/{userId}")
    public Response<CommentResponseDto> postNewComment(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId, @RequestBody CommentRequestDto request) {
        CommentResponseDto savedComment = commentService.upload(postId, userId, request);

        return Response.success("완료", savedComment);
    }

    @PatchMapping("/{postId}/update/{commentId}/{userId}")
    public Response<CommentResponseDto> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @PathVariable("userId") Long userId, @RequestBody CommentRequestDto request) {
        CommentResponseDto updatedComment = commentService.updateComment(postId, commentId, userId, request);
        return Response.success("댓글 수정 완료", updatedComment);
    }

    @DeleteMapping("/{postId}/delete/{commentId}")
    public Response<String> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        commentService.delete(postId, commentId);
        return Response.success("댓글을 삭제하였습니다.", null);
    }
}
