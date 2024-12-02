package uz.pdp.giftcertificate.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseResponse {
    private UUID id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
