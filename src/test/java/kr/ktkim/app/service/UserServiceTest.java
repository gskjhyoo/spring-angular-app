package kr.ktkim.app.service;

import kr.ktkim.app.SpringAngularAppApplication;
import kr.ktkim.app.model.User;
import kr.ktkim.app.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Keumtae Kim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringAngularAppApplication.class)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {

        String login = "user000";
        String password = "pass000";
        String name = "user000";
        String email = "user000@test.com";

        User user = userService.createUser(login, password, name, email);
        Optional<User> maybeUser = userRepository.findOneByLogin(login);
        assertThat(maybeUser.isPresent()).isTrue();
        assertThat(user).isEqualTo(maybeUser.get());
    }

    @Test
    public void testGetAuthorities() {
        List<String> authorities = userService.getAuthorities();
        assertThat(authorities.size()).isEqualTo(2);
    }
}
