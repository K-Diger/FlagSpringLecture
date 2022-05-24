package KimDoHyeon.lecture.domain.post;

import lombok.Data;

public class PostRequestDto {

    @Data
    public static class WritePostForm {
        private String postMbti;
        private String content;
    }

    @Data
    public static class GetPostForm {
        private String postMbti;
    }

    @Data
    public static class PatchPostForm {
        private Long id;
        private String content;
    }

    @Data
    public static class DeletePostForm {
        private Long id;
    }
}
