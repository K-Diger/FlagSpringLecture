package KimDoHyeon.lecture.domain.user;


import KimDoHyeon.lecture.global.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    //User 관련 의존성
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;

    @PostMapping("/id-check")
    public ResponseEntity<UserResponseDto.CheckIdResponseForm> checkId(@Valid @RequestBody UserRequestDto.CheckIdForm checkIdForm) {

        UserResponseDto.CheckIdResponseForm responseForm = new UserResponseDto.CheckIdResponseForm();

        HashMap<String, Boolean> response = new HashMap<>();

        // 아이디가 중복됐다면 false
        if (userService.isOverlapId(checkIdForm.getLoginId())) {
            response.put("Available", false);

        } else {
            response.put("Available", true);
        }
        responseForm.setResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }

    @PostMapping("/join")
    public ResponseEntity<UserResponseDto.JoinResponseForm> join(@Valid @RequestBody UserRequestDto.JoinForm joinForm) {

        UserResponseDto.JoinResponseForm responseForm = new UserResponseDto.JoinResponseForm();

        HashMap<String, Boolean> response = new HashMap<>();

        // 아이디가 중복됐다면 false
        if (userService.isOverlapId(joinForm.getLoginId())) {
            response.put("Success", false);

            responseForm.setResponse(response);

        } else {
            User newUser = User.builder()
                    .loginId(joinForm.getLoginId())
                    .pw(bCryptPasswordEncoder.encode(joinForm.getPw()))
                    .mbti(joinForm.getMbti())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

//            userService.saveUser(newUser);
            userRepository.save(newUser);

            response.put("Success", true);

            responseForm.setResponse(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto.LoginResponseForm> checkId(@Valid @RequestBody UserRequestDto.LoginForm loginForm) {

        UserResponseDto.LoginResponseForm responseForm = new UserResponseDto.LoginResponseForm();

        HashMap<String, String> response = new HashMap<>();

        // 아이디 비밀번호 일치 시
        if (userService.checkPw(loginForm.getLoginId(), loginForm.getPw())) {

            // AccessToken 발급
            String accessToken = jwtProvider.createAccessToken(userService.loadUserByLoginId(loginForm.getLoginId()));

            // 반환 객체에 AccessToken 넣기
            response.put("AccessToken", accessToken);

            // 반환 객체 DTO 로 감싸기
            responseForm.setResponse(response);

            return ResponseEntity.status(HttpStatus.OK).body(responseForm);
        }

        // 아이디나 비밀번호가 일치 하지 않거나 없는 아이디 일 경우

        response.put("Success", "존재 하지 않는 아이디 이거나, 비밀번호가 일치하지 않습니다.");

        responseForm.setResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }
}
