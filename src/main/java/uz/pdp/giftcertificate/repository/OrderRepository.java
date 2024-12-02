package uz.pdp.giftcertificate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.giftcertificate.domain.entity.OrderEntity;
import uz.pdp.giftcertificate.domain.views.OrderView;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Page<OrderEntity> findAllByUserIdOrderByCreatedDate(Pageable pageable, UUID userId);
    Page<OrderEntity> findAllByUserId(Pageable pageable, UUID userId);
    Page<OrderView> findAllOrderEntitiesByUserId(Pageable pageable, UUID userId);


}
