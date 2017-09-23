package kr.ktkim.app.api;

import kr.ktkim.app.model.UserDTO;
import kr.ktkim.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keumtae Kim
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @GetMapping("/users/{login}")
    public Object getUser(@PathVariable String login) {
        return userService.findOneByLogin(login)
                .map(UserDTO::new)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
