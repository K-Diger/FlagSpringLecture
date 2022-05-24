package KimDoHyeon.lecture.domain.comment;


import KimDoHyeon.lecture.domain.post.Post;
import KimDoHyeon.lecture.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;

@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final PostService postService;

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto.WriteCommentResponse> writeComment(@Valid @RequestBody CommentRequestDto.WriteCommentForm writeCommentForm) {

        HashMap<String, Boolean> response = new HashMap<>();

        CommentResponseDto.WriteCommentResponse responseForm = new CommentResponseDto.WriteCommentResponse();

        Comment newComment = Comment.builder()
                .post(postService.loadPostByPostIndex(writeCommentForm.getPostIdx()))
                .content(writeCommentForm.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        commentRepository.save(newComment);

        response.put("Success", true);

        responseForm.setResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }

    @GetMapping
    public ResponseEntity<CommentResponseDto.GetCommentResponse> getComment(
            @Valid @RequestBody CommentRequestDto.GetCommentForm getCommentForm, Pageable page) {

        CommentResponseDto.GetCommentResponse responseForm = new CommentResponseDto.GetCommentResponse();

        responseForm.setResponse(commentRepository.findByPostId(getCommentForm.getPostIdx(), page));

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }

    @PatchMapping
    public ResponseEntity<CommentResponseDto.PatchCommentResponse> patchComment(
            @Valid @RequestBody CommentRequestDto.PatchCommentForm patchCommentForm) {

        HashMap<String, Boolean> response = new HashMap<>();

        CommentResponseDto.PatchCommentResponse responseForm = new CommentResponseDto.PatchCommentResponse();

        commentService.editComment(patchCommentForm);

        response.put("Success", true);

        responseForm.setResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);

    }

    @DeleteMapping
    public ResponseEntity<CommentResponseDto.DeleteCommentResponse> deleteComment(
            @Valid @RequestBody CommentRequestDto.DeleteCommentForm deleteCommentForm) {

        HashMap<String, Boolean> response = new HashMap<>();

        CommentResponseDto.DeleteCommentResponse responseForm = new CommentResponseDto.DeleteCommentResponse();

        commentRepository.delete(commentService.loadCommentByIndex(deleteCommentForm.getId()));

        response.put("Success", true);

        responseForm.setResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }
}
