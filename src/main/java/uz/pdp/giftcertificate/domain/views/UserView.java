package uz.pdp.giftcertificate.domain.views;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserView {
    String getUsername();
    UUID getId();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatedDate();

}
