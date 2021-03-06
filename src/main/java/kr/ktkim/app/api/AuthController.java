package kr.ktkim.app.api;

import kr.ktkim.app.security.jwt.JwtAuthRequest;
import kr.ktkim.app.security.jwt.JwtAuthResponse;
import kr.ktkim.app.security.jwt.JwtFilter;
import kr.ktkim.app.security.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.Collections;

import static kr.ktkim.app.security.jwt.JwtFilter.AUTHORIZATION_HEADER;

/**
 * @author Keumtae Kim
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthRequest request
            , @RequestParam(value = "rememberMe", defaultValue = "false", required = false) boolean rememberMe
            , HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = "Bearer " + jwtUtil.createToken(authentication, rememberMe);
            response.addHeader(AUTHORIZATION_HEADER, jwt);
            return ResponseEntity.ok(new JwtAuthResponse(jwt));
        } catch (AuthenticationException ae) {
            logger.trace("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}