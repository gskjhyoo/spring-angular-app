package kr.ktkim.app.repository;

import kr.ktkim.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Keumtae Kim
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneById(Long id);
    Optional<User> findOneByLogin(String login);
}
