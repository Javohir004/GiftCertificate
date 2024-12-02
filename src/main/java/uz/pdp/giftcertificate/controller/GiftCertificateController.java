package uz.pdp.giftcertificate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.giftcertificate.domain.dto.request.GiftCertificateCreateDTO;
import uz.pdp.giftcertificate.domain.dto.response.GiftCertificateResponse;
import uz.pdp.giftcertificate.domain.entity.UserEntity;
import uz.pdp.giftcertificate.service.GiftCertificateService;
import uz.pdp.giftcertificate.service.SecurityTokenService;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/gift-certificate")
public class GiftCertificateController extends BaseController {
    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private SecurityTokenService tokenService;

    @PostMapping
    public GiftCertificateResponse create(@Valid @RequestBody GiftCertificateCreateDTO createDTO) {
        return giftCertificateService.save(createDTO);
    }

    @PatchMapping("/{id}")
    public GiftCertificateResponse update(@PathVariable(name = "id") UUID id, @RequestBody GiftCertificateCreateDTO updateDTO) throws MethodArgumentNotValidException, NoSuchMethodException {
        return giftCertificateService.update(updateDTO, id);
    }

    @GetMapping("/filter")
    public Page<GiftCertificateResponse> filter(
            @RequestHeader(value = "auth") String authToken,
            @RequestParam(value = "tags", defaultValue = "[]") Set<String> tags,
            @RequestParam(value = "word", defaultValue = "") String word,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size)
    {
        UserEntity user = tokenService.decode(authToken);
        return giftCertificateService.filter(tags, word, page, size);
    }

}
