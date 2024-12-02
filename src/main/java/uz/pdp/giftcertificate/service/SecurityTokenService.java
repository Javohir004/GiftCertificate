package uz.pdp.giftcertificate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.giftcertificate.exception.AuthException;
import uz.pdp.giftcertificate.domain.entity.SecurityToken;
import uz.pdp.giftcertificate.domain.entity.UserEntity;
import uz.pdp.giftcertificate.repository.SecurityTokenRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

@Service
public class SecurityTokenService {
    @Autowired
    private Base64.Encoder encoder;

    @Autowired
    private Base64.Decoder decoder;

    @Autowired
    private SecurityTokenRepository securityTokenRepository;

    @Value("${security.secret-key}")
    private String secretKey;



    public String generateToken(UserEntity user) {
        String tokenOrigin = user.getId().toString() + "T" + System.currentTimeMillis() + secretKey;
        String token = new String(encoder.encode(tokenOrigin.getBytes()));
        securityTokenRepository.save(new SecurityToken(user,token, calculateExpiryDate()));
        return token;
    }

    public UserEntity decode(String token) {
        String decodedToken = new String(decoder.decode(token));
        UUID userId = UUID.fromString(decodedToken.split("T")[0]);
        SecurityToken securityToken = securityTokenRepository.findByUserIdAndExpiryDateIsAfter(userId, LocalDateTime.now())
                .orElseThrow(() -> new AuthException("authorization exception, token expired or user not found"));
        return securityToken.getUser();
    }

    public LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plus(30, ChronoUnit.MINUTES);
    }
}
