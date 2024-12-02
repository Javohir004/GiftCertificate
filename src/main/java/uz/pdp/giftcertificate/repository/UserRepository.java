package uz.pdp.giftcertificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.giftcertificate.domain.entity.UserEntity;
import uz.pdp.giftcertificate.domain.views.UserView;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserView> findUserEntityById(UUID id);
}
