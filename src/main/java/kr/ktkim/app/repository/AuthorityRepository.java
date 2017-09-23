package kr.ktkim.app.repository;

import kr.ktkim.app.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Keumtae Kim
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
