package KimDoHyeon.lecture.domain.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDto {

    @Data
    public static class CheckIdForm {
        private String loginId;
    }

    @Data
    public static class JoinForm {
        private String loginId;
        private String pw;
        private String mbti;
    }

    @Data
    public static class LoginForm {
        private String loginId;
        private String pw;
    }
}
