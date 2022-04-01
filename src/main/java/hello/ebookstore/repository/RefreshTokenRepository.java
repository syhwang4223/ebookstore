package hello.ebookstore.repository;

import hello.ebookstore.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Refresh Token 을 저장하기 위한 레포지토리
 */

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    // memberId 값으로 토큰을 가져온다.
    Optional<RefreshToken> findByKey(String key);
}
