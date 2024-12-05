package capstone.checkIT.utils;

import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.apipayLoad.handler.TempHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecretKey key = Jwts.SIG.HS256.key().build();


    private static final long ACCESS_TOKEN_EXPIRATION = 3600000;

    public String createAccessToken(Long id) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key)
                .compact();
    }


    public Long validateToken(String token) {
        try{
            Claims claims = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Long.parseLong(claims.getSubject());
        }
        catch(ExpiredJwtException e){
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }
        catch (JwtException e) {
            throw new TempHandler(ErrorStatus.TOKEN_UNVALID);
        }
        catch (Exception e) {
            throw new TempHandler(ErrorStatus.TOKEN_UNKNOWN_ERROR);
        }
    }

    public Long validateAccessToken(String token){
        return validateToken(token);
    }



}