package uz.pdp.giftcertificate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import uz.pdp.giftcertificate.exception.BaseException;
import uz.pdp.giftcertificate.domain.dto.request.GiftCertificateCreateDTO;
import uz.pdp.giftcertificate.domain.dto.response.GiftCertificateResponse;
import uz.pdp.giftcertificate.domain.entity.GiftCertificateEntity;
import uz.pdp.giftcertificate.repository.GiftCertificateRepository;

import java.lang.reflect.Method;
import java.util.*;

@Service
public class GiftCertificateService implements BaseService<GiftCertificateEntity, GiftCertificateResponse, GiftCertificateCreateDTO> {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private ModelMapper modelMapper;

    public GiftCertificateResponse save(GiftCertificateCreateDTO createDTO) {
        GiftCertificateEntity giftCertificateEntity = mapRequestToEntity(createDTO);
        return mapEntityToResponse(giftCertificateRepository.save(giftCertificateEntity));
    }

    public GiftCertificateResponse update(GiftCertificateCreateDTO createDTO, UUID id) throws MethodArgumentNotValidException, NoSuchMethodException {
        validate(createDTO);
        GiftCertificateEntity certificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new BaseException("certificate with id " + id + " not found"));
        modelMapper.map(createDTO, certificate);

        return mapEntityToResponse(giftCertificateRepository.save(certificate));
    }

    public Page<GiftCertificateResponse> filter(Set<String> tags, String word, int page, int size) {
        if(tags.isEmpty()) {
            return giftCertificateRepository.filterNoTag(word, PageRequest.of(page, size))
                    .map(this::mapEntityToResponse);
        }

//        return giftCertificateRepository.filterNativeQuery(tags, tags.size(), word, PageRequest.of(page, size))
//                .map(this::mapEntityToResponse);
        return giftCertificateRepository.filter(tags, tags.size(), word, PageRequest.of(page, size))
                .map(this::mapEntityToResponse);
    }


    public void validate(GiftCertificateCreateDTO updateDto) throws NoSuchMethodException, MethodArgumentNotValidException {
        BindingResult bindingResult = new BeanPropertyBindingResult(updateDto, "giftCertificateUpdateDTO");

        if(Objects.nonNull(updateDto.getName()) && updateDto.getName().isBlank()) {
                bindingResult.addError(new FieldError("updateDto", "name", "name cannot be blank"));
            }

        if (Objects.nonNull(updateDto.getDescription()) && updateDto.getDescription().isBlank()) {
                bindingResult.addError(new FieldError("updateDto", "description", "description cannot be blank"));
        }


        if(Objects.nonNull(updateDto.getPrice()) && updateDto.getPrice() < 0) {
            bindingResult.addError(new FieldError("updateDto", "price", "price cannot be negative value"));
        }

        if(Objects.nonNull(updateDto.getDuration()) && updateDto.getDuration() < 1) {
            bindingResult.addError(new FieldError("updateDto", "duration", "duration must be at least 1 day"));
        }

        if (bindingResult.hasErrors()) {
            Method method = GiftCertificateService.class.getMethod("update", GiftCertificateCreateDTO.class, UUID.class);
            MethodParameter methodParameter = new MethodParameter(method, 0);
            // Create MethodArgumentNotValidException
            throw new MethodArgumentNotValidException(methodParameter, bindingResult);

        }

    }
    @Override
    public GiftCertificateEntity mapRequestToEntity(GiftCertificateCreateDTO createDTO) {
        GiftCertificateEntity certificate = modelMapper.map(createDTO, GiftCertificateEntity.class);
        if (Objects.nonNull(createDTO.getTags())) {
            certificate.setTags(tagService.findOrSaveTagsFromSet(createDTO.getTags()));
        }
        return certificate;
    }

    @Override
    public GiftCertificateResponse mapEntityToResponse(GiftCertificateEntity entity) {
        return modelMapper.map(entity, GiftCertificateResponse.class);
    }
}
