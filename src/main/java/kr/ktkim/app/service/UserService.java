package kr.ktkim.app.service;

import kr.ktkim.app.model.Authority;
import kr.ktkim.app.model.User;
import kr.ktkim.app.model.UserDTO;
import kr.ktkim.app.repository.AuthorityRepository;
import kr.ktkim.app.repository.UserRepository;
import kr.ktkim.app.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public UserDTO findOneById(Long id) {
        return new UserDTO(userRepository.findOneById(id).get());
    }

    public Optional<User> findOneByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public void updateUser(Long id, String email, String name, boolean activated) {
        userRepository.findOneById(id).ifPresent(user -> {
            user.setEmail(email);
            user.setName(name);
            user.setActivated(activated);
            log.debug("Changed Information for User: {}", user);
        });
    }

    public Optional<UserDTO> updateUser(UserDTO userDTO) {
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
                }).map(UserDTO::new);
    }

    public Page<UserDTO> findAllUser(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        Page<UserDTO> userDTOs = page.map(user -> new UserDTO(user));
        return userDTOs;
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
