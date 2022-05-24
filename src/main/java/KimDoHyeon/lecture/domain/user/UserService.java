package KimDoHyeon.lecture.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public boolean isOverlapId(String loginId) {
        return userRepository.findByLoginId(loginId).isPresent();
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public boolean checkPw(String loginId, String pw) {

        if (userRepository.findByLoginId(loginId).isPresent()) {
            return bCryptPasswordEncoder.matches(pw, userRepository.findByLoginId(loginId).get().getPw());
        } else {
            // 로그인 아이디가 DB 에 없으면 (원래는 예외처리로 에러를 터뜨려 줘야하지만 생략)
            return false;
        }
    }

    @Transactional
    public User loadUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).get();
    }
}