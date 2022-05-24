package KimDoHyeon.lecture.domain.post;


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

    private final PostService postService;

    private final PostRepository postRepository;


    @PostMapping
    public ResponseEntity<PostResponseDto.WritePostResponse> writePost(@Valid @RequestBody PostRequestDto.WritePostForm writePostForm) {

        HashMap<String, Boolean> response = new HashMap<>();

        PostResponseDto.WritePostResponse responseForm = new PostResponseDto.WritePostResponse();

        Post newPost = Post.builder()
                .postMbti(writePostForm.getPostMbti())
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
