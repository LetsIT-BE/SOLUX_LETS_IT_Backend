package letsit_backend.controller;

import jakarta.validation.Valid;
import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.dto.Response;
import letsit_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/delete/{postId}")
    public Response<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto requestDto) {
        PostResponseDto responseDto = postService.createPost(requestDto);
        return Response.success("구인 글이 성공적으로 등록되었습니다.", responseDto);
    }

    @DeleteMapping("/{userId}/delete/{postId}")
    public ResponseEntity<Response<Void>> deletePost(@PathVariable Long postId) {
        boolean isDeleted = postService.deletePost(postId);

        if (isDeleted) {
            return ResponseEntity.ok(new Response<>(true, "구인 글이 성공적으로 삭제되었습니다.", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, "인증이 필요합니다. 로그인 후 다시 시도해 주세요.", null));
        }
    }

//    @GetMapping("/list/{postId}")
//    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
//        try {
//            PostResponseDto postResponseDto = postService.getPostById(postId);
//            return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(false, "Invalid region parameter"));
//        }
//    }
}
