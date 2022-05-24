package KimDoHyeon.lecture.global.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator {

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    //AccessToken 만료날짜 확인
    public boolean validateAccessToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(accessToken);
            return true;
        } catch (SignatureException | MalformedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID", ex);
        } catch (ExpiredJwtException ignored) {
        }
        return false;
    }
}
