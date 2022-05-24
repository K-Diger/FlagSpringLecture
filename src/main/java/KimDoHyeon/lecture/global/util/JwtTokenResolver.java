package KimDoHyeon.lecture.global.util;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static io.jsonwebtoken.Jwts.parser;

@Component
@RequiredArgsConstructor
public class JwtTokenResolver {

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    //AccessToken 에서 userIdx 꺼내기
    public Long getId(String token) {

        // Object Type 으로 받는다. (Long 으로 강제 형변환이 안되어서 한번 거쳤다가 Long 으로 )
        // 기존에는 Integer 로 받았지만, Integer 범위를 넘어서는 값이 등장하면 런타임 에러가 발생한다.
        Object id = parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().get("id");

        // ObjectType 을 Long 으로 변환
        return Long.valueOf(String.valueOf(id));
    }
}
