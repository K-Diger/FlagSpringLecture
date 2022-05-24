package KimDoHyeon.lecture.domain.comment;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CommentResponseDto {

    @Data
    public static class WriteCommentResponse {
        private HashMap<String, Boolean> response;
    }

    @Data
    public static class GetCommentResponse {
        private List<Comment> response;
    }

    @Data
    public static class PatchCommentResponse {
        private HashMap<String, Boolean> response;
    }

    @Data
    public static class DeleteCommentResponse {
        private HashMap<String, Boolean> response;
    }

}
