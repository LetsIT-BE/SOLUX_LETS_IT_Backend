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

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/upload")
//    public Response<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto requestDto) {
//        PostResponseDto responseDto = postService.createPost(requestDto);
//        return Response.success("구인 글이 성공적으로 등록되었습니다.", responseDto);
//    }
    public Response<PostResponseDto> createPost(@PathVariable Long userId, @Valid @RequestBody PostRequestDto requestDto) {
        requestDto.setUserId(userId);  // URL에서 전달된 userId를 requestDto에 설정
        PostResponseDto responseDto = postService.createPost(requestDto);
        return Response.success("구인 글이 성공적으로 등록되었습니다.", responseDto);
    }

    // 게시글 수정
    @PutMapping("/{postId}/update")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        try {
            PostResponseDto updatedPost = postService.updatePost(postId, postRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(true, "구인 글이 성공적으로 수정되었습니다.", updatedPost));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(false, "유효성 검사 오류", e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/delete/{postId}")
    public ResponseEntity<Response<Void>> deletePost(@PathVariable Long userId, @PathVariable Long postId) {
        boolean isDeleted = postService.deletePost(userId, postId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(Response.success("게시글이 성공적으로 삭제되었습니다.", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.fail("인증이 필요합니다. 로그인 후 다시 시도해 주세요."));
        }
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<Response<Void>> deletePost(@RequestParam Long userId, @RequestParam Long postId) {
//        boolean isDeleted = postService.deletePost(userId, postId);
//
////        if (isDeleted) {
////            return ResponseEntity.ok(new Response<>(true, "구인 글이 성공적으로 삭제되었습니다.", null));
////        } else {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(false, "인증이 필요합니다. 로그인 후 다시 시도해 주세요.", null));
////        }
//        if (isDeleted) {
//            return ResponseEntity.status(HttpStatus.OK).body(Response.success("게시글이 성공적으로 삭제되었습니다.", null));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.fail("인증이 필요합니다. 로그인 후 다시 시도해 주세요."));
//        }
//    }

    @GetMapping("{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        try {
            PostResponseDto postResponseDto = postService.getPostById(postId);
            return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(false, "Invalid region parameter"));
        }
    }

//    @GetMapping("/list")
//    public ResponseEntity<?> getAllPosts() {
//        try {
//            List<PostResponseDto> posts = postService.getAllPosts();
//            return ResponseEntity.ok(posts);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Bad Request", "message", "Invalid sort parameter"));
//        }
//    }


    @PostMapping("/{postId}/close")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> closePost(@PathVariable Long postId) {
        boolean isClosed = postService.closePost(postId);
        if (isClosed) {
            return new Response<>(true, "모집이 마감되었습니다.");
        } else {
            return new Response<>(false, "모집 마감에 실패했습니다.");
        }
    }

    // 최신순으로 모든 게시글 조회
    @GetMapping("/list")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPostsOrderByCreatedAt();
        return ResponseEntity.ok(posts);
    }

//    // 스크랩순으로 모든 게시글 조회
//    @GetMapping("/list/scrap")
//    public ResponseEntity<List<PostResponseDto>> getAllPostsOrderByScrapCount() {
//        List<PostResponseDto> posts = postService.getAllPostsOrderByScrapCount();
//        return ResponseEntity.ok(posts);
//    }
//
//    // 조회순으로 모든 게시글 조회
//    @GetMapping("/list/view")
//    public ResponseEntity<List<PostResponseDto>> getAllPostsOrderByViewCount() {
//        List<PostResponseDto> posts = postService.getAllPostsOrderByViewCount();
//        return ResponseEntity.ok(posts);
//    }
}
