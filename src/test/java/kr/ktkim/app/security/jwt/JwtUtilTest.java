package kr.ktkim.app.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.ktkim.app.config.ApplicationProperties;
import kr.ktkim.app.security.AuthoritiesConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Keumtae Kim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JwtUtil.class, ApplicationProperties.class})
@EnableConfigurationProperties
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Test
    public void testInvalidSecretToken() {
        boolean isTokenValid = jwtUtil.validateToken(createToeknWithWrongSecret());
        assertThat(isTokenValid).isEqualTo(false);
    }

    @Test
    public void testExpiredToken() {
        boolean isTokenValid = jwtUtil.validateToken(createExpiredToken());
        assertThat(isTokenValid).isEqualTo(false);
    }

    @Test
    public void testReturnFalseWhenJWTisExpired() {
        Authentication authentication = createAuthentication();
        String token = jwtUtil.createToken(authentication, false);

        boolean isTokenValid = jwtUtil.validateToken(token);

        assertThat(isTokenValid).isEqualTo(false);
    }

    private String createToeknWithWrongSecret() {
        return Jwts.builder()
                .setSubject("admin")
                .claim("role", "ROLE_ADMIN")
                .signWith(SignatureAlgorithm.HS512, "wrongSecret")
                .setExpiration(new Date((new Date()).getTime() + 1000 * 3600 * 24 * 365))
                .compact();
    }

    private String createExpiredToken() {
        return Jwts.builder()
                .setSubject("admin")
                .claim("role", "ROLE_ADMIN")
                .signWith(SignatureAlgorithm.HS512, applicationProperties.getSecurity().getJwt().getSecret())
                .setExpiration(new Date((new Date()).getTime() - 1000))
                .compact();
    }

    private Authentication createAuthentication() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        return new UsernamePasswordAuthenticationToken("anonymous", "anonymous", authorities);
    }

}
