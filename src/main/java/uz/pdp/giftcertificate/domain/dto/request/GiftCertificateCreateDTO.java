package uz.pdp.giftcertificate.domain.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCertificateCreateDTO {

    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "name cannot be blank")
    private String description;
    @Min(1)
    private Integer duration;
    @Min(0)
    private Double price;
    private Set<String> tags;

}
