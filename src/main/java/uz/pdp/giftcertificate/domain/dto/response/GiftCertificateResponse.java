package uz.pdp.giftcertificate.domain.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateResponse extends BaseResponse{
    private String name;
    private String description;
    private int duration;
    private Double price;
    private List<TagResponse> tags;

}
