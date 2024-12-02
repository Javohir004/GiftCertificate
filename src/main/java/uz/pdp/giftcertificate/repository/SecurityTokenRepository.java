package uz.pdp.giftcertificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.giftcertificate.domain.entity.SecurityToken;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecurityTokenRepository extends JpaRepository<SecurityToken, UUID> {

    Optional<SecurityToken> findByUserIdAndExpiryDateIsAfter(UUID userId, LocalDateTime currentTime);
}
