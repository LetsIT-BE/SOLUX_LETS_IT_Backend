package letsit_backend.controller;

import jakarta.validation.Valid;
import letsit_backend.dto.PostRequestDto;
import letsit_backend.dto.PostResponseDto;
import letsit_backend.dto.Response;
import letsit_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto requestDto) {
        PostResponseDto responseDto = postService.createPost(requestDto);
        return Response.success("구인 글이 성공적으로 등록되었습니다.", responseDto);
    }
}
