package kr.ktkim.app.api;

import kr.ktkim.app.common.PaginationUtil;
import kr.ktkim.app.model.Authority;
import kr.ktkim.app.model.User;
import kr.ktkim.app.model.UserDto;
import kr.ktkim.app.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Keumtae Kim
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private static final String CHECK_ERROR_MESSAGE = "Incorrect password";

    @PostMapping(path = "/register",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity registerAccount(@Valid @RequestBody UserDto.Create userDTO) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        if (!!StringUtils.isEmpty(userDTO.getPassword()) &&
                userDTO.getPassword().length() >= 4 && userDTO.getPassword().length() <= 100) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        return userService.findOneByLogin(userDTO.getLogin().toLowerCase())
                .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> userService.findOneByEmail(userDTO.getEmail())
                        .map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                        .orElseGet(() -> {
                            User user = userService
                                    .createUser(userDTO.getLogin(), userDTO.getPassword(),
                                            userDTO.getName(), userDTO.getEmail().toLowerCase());
                            return new ResponseEntity<>(HttpStatus.CREATED);
                        })
                );
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<UserDto.Response> getUser(@PathVariable String login) {
        return userService.findOneByLogin(login)
                .map(user -> modelMapper.map(user, UserDto.Response.class))
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto.Response>> getAllUsers(Pageable pageable) {
        Page<User> page = userService.findAllUser(pageable);
        Page<UserDto.Response> pageResult = page.map(user -> modelMapper.map(user, UserDto.Response.class));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(pageResult.getContent(), headers, HttpStatus.OK);
    }
}
