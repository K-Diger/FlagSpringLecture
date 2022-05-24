package KimDoHyeon.lecture.domain.post;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class PostResponseDto {

    @Data
    public static class WritePostResponse {
        private HashMap<String, Boolean> response;
    }

    @Data
    public static class GetPostResponse {
        private List<Post> response;
    }

    @Data
    public static class PatchPostResponse {
        private HashMap<String, Boolean> response;
    }

    @Data
    public static class DeletePostResponse {
        private HashMap<String, Boolean> response;
    }

}
