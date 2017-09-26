package kr.ktkim.app.service;

import kr.ktkim.app.model.Authority;
import kr.ktkim.app.model.User;
import kr.ktkim.app.model.UserDto;
import kr.ktkim.app.repository.AuthorityRepository;
import kr.ktkim.app.repository.UserRepository;
import kr.ktkim.app.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Keumtae Kim
 */
@Service
@Transactional
public class UserService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String email, String name) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setActivated(true);
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(String email, String name, Set<String> authorities) {
        User newUser = new User();
        Set<Authority> managedAuthorities = new HashSet<>();
        authorities.stream()
                .map(authorityRepository::findOne)
                .forEach(managedAuthorities::add);

        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setActivated(false);
        newUser.setAuthorities(managedAuthorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(String login, String password, String name, String email) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        newUser.setPassword(encryptedPassword);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setActivated(false);
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User findOneById(Long id) {
        User user = userRepository.findOneById(id).get();
        if (user == null) {
            throw new RuntimeException("");
        }
        return user;
    }

    public Optional<User> findOneByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public Optional<User> findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public void updateUser(Long id, String email, String name, boolean activated) {
        userRepository.findOneById(id).ifPresent(user -> {
            user.setEmail(email);
            user.setName(name);
            user.setActivated(activated);
            log.debug("Changed Information for User: {}", user);
        });
    }

    public Optional<UserDto.Response> updateUser(UserDto.Update userDTO) {
        return Optional.of(userRepository.findOne(userDTO.getId()))
                .map(user -> {
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream()
                            .map(authorityRepository::findOne)
                            .forEach(managedAuthorities::add);
                    user.setActivated(userDTO.isActivated());
                    log.debug("Changed Information for User: {}", user);
                    return user;
                }).map(user -> {
            UserDto.Response response = new UserDto.Response();
            response.setLogin(user.getLogin());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setActivated(user.getActivated());
            response.setAuthorities(user.getAuthorities());
            return response;
        });
    }

    public Page<User> findAllUser(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        userRepository.findOneById(userId).ifPresent(user -> {
            userRepository.delete(userId);
        });
    }
}