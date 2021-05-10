package module2.lesson4.task1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Date;

@Component
public class JWTProvider {
    String secretkey = "lyuboynarsa";
    long expireTime = 36000000;

    public String generateToken(String userName) {
        String compact = Jwts
                .builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretkey)
                .compact();
        return compact;
    }

    public boolean validateToken(String token) {
        try {

            Jwts
                    .parser()
                    .setSigningKey(secretkey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token){
        String userName = Jwts
                .parser()
                .setSigningKey(secretkey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userName;
    }


}
