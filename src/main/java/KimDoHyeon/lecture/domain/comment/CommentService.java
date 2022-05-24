package KimDoHyeon.lecture.domain.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    @Transactional
    public Comment loadCommentByIndex(Long id) {
        return commentRepository.findById(id).get();
    }

    @Transactional
    public void editComment(CommentRequestDto.PatchCommentForm patchCommentForm) {
        Comment oldComment = commentRepository.findById(patchCommentForm.getId()).get();

        oldComment.setContent(patchCommentForm.getContent());
        oldComment.setUpdatedAt(LocalDateTime.now());

    }
}
