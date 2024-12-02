package uz.pdp.giftcertificate.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity extends BaseEntity {
    @ManyToOne
    private GiftCertificateEntity certificate;
    @ManyToOne
    private UserEntity user;
    private Double price;
}
