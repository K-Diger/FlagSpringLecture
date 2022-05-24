package KimDoHyeon.lecture.domain.comment;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class CommentRequestDto {

    @Data
    public static class WriteCommentForm {
        private Long postIdx;
        private String content;
    }

    @Data
    public static class GetCommentForm {
        private Long postIdx;
    }

    @Data
    public static class PatchCommentForm {
        private Long id;
        private String content;
    }

    @Data
    public static class DeleteCommentForm {
        private Long id;
    }
}
