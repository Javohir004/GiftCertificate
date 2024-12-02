package uz.pdp.giftcertificate.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SecurityToken extends BaseEntity {
    @ManyToOne
    private UserEntity user;
    private String token;
    private LocalDateTime expiryDate;
}
