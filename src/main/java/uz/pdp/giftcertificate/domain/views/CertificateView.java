package uz.pdp.giftcertificate.domain.views;

import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface CertificateView {
    UUID getId();
    String getName();
    @Value("#{target.name + ' ' + target.description}")
    String getFullInfo();
}
