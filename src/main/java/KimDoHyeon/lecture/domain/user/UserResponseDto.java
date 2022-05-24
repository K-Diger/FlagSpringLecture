package KimDoHyeon.lecture.domain.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserResponseDto {

    @Data
    public static class CheckIdResponseForm {
        private HashMap<String, Boolean> response;
    }

    @Data
    public static class JoinResponseForm {
        private HashMap<String, Boolean> response;
    }

    @Data
    public static class LoginResponseForm {
        private HashMap<String, String> response;
    }

}
