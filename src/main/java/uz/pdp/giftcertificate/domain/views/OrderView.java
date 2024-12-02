package uz.pdp.giftcertificate.domain.views;

import java.util.UUID;

public interface OrderView {
    UUID getId();
    CertificateView getCertificate();
    UserView getUser();
    Double getPrice();

}
