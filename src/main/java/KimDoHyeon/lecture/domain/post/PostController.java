package KimDoHyeon.lecture.domain.post;


import KimDoHyeon.lecture.domain.user.User;
import KimDoHyeon.lecture.domain.user.UserRepository;
import KimDoHyeon.lecture.domain.user.UserService;
import KimDoHyeon.lecture.global.util.JwtTokenResolver;
import KimDoHyeon.lecture.global.util.JwtTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;


@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepository;

    private final PostService postService;

    private final PostRepository postRepository;

    private final JwtTokenResolver jwtTokenResolver;

    private final JwtTokenValidator jwtTokenValidator;


    @PostMapping
    public ResponseEntity<PostResponseDto.WritePostResponse> writePost(@Valid @RequestHeader String Authorization,
                                                                       @Valid @RequestBody PostRequestDto.WritePostForm writePostForm) {

        // 예외처리 생략
        jwtTokenValidator.validateAccessToken(Authorization);

        Long userIdx = jwtTokenResolver.getId(Authorization);

        User requestUser = userRepository.findById(userIdx).get();

        HashMap<String, Boolean> response = new HashMap<>();

        PostResponseDto.WritePostResponse responseForm = new PostResponseDto.WritePostResponse();

        Post newPost = Post.builder()
                .postMbti(writePostForm.getPostMbti())
                .user(requestUser)
                .content(writePostForm.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        postRepository.save(newPost);

        response.put("Success", true);

        responseForm.setResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }

    @GetMapping
    public ResponseEntity<PostResponseDto.GetPostResponse> getPost(@Valid @RequestBody PostRequestDto.GetPostForm getPostForm, Pageable page) {

        PostResponseDto.GetPostResponse responseForm = new PostResponseDto.GetPostResponse();

        responseForm.setResponse(postRepository.findByPostMbti(getPostForm.getPostMbti(), page));

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }

    @PatchMapping
    public ResponseEntity<PostResponseDto.PatchPostResponse> patchPost(@Valid @RequestBody PostRequestDto.PatchPostForm patchPostForm) {

        HashMap<String, Boolean> response = new HashMap<>();

        PostResponseDto.PatchPostResponse responseForm = new PostResponseDto.PatchPostResponse();
        
        
        // 글 수정 요청 성공 시
        if (postService.editPost(patchPostForm.getId(), patchPostForm.getContent())) {

            response.put("Success", true);

            responseForm.setResponse(response);

            return ResponseEntity.status(HttpStatus.OK).body(responseForm);
        }

        // 글 수정 요청 실패 시
        response.put("Success", false);

        responseForm.setResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);

    }

    @DeleteMapping
    public ResponseEntity<PostResponseDto.DeletePostResponse> deletePost(@Valid @RequestBody PostRequestDto.DeletePostForm deletePostForm) {

        HashMap<String, Boolean> response = new HashMap<>();

        PostResponseDto.DeletePostResponse responseForm = new PostResponseDto.DeletePostResponse();


        Optional<Post> targetPost = postRepository.findById(deletePostForm.getId());

        if (targetPost.isPresent()) {
            postRepository.delete(targetPost.get());

            // 글 삭제 요청 성공 시
            response.put("Success", true);

            responseForm.setResponse(response);

            return ResponseEntity.status(HttpStatus.OK).body(responseForm);
        }

        // 글 삭제 요청 성공 시
        response.put("Success", false);

        responseForm.setResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);

    }

}
