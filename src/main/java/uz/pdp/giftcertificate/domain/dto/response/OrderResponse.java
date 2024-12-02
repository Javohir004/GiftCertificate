package uz.pdp.giftcertificate.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse extends BaseResponse {
    private String certificateName;
    private UUID certificateId;
    private Double price;

}
