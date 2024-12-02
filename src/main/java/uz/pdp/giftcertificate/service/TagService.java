package uz.pdp.giftcertificate.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.giftcertificate.domain.dto.response.TagResponse;
import uz.pdp.giftcertificate.domain.entity.BaseEntity;
import uz.pdp.giftcertificate.domain.entity.TagEntity;
import uz.pdp.giftcertificate.repository.TagRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService implements BaseService<TagEntity, TagResponse, String> {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TagEntity save(String name) {
        return tagRepository.save(mapRequestToEntity(name));
    }

    public List<TagResponse> findAll() {
        List<TagEntity> tagEntities = tagRepository.findAll();
        return modelMapper.map(tagEntities, new TypeReference<List<TagResponse>>(){}.getType());
    }

    public Set<TagEntity> findOrSaveTagsFromSet(Set<String> tags) {
        Set<TagEntity> tagEntities = tagRepository.findAllByNameIn(tags);
        List<TagEntity> unsaved = tags.stream().map(TagEntity::new)
                .collect(Collectors.toList());
        unsaved.removeAll(tagEntities);
        tagEntities.addAll(tagRepository.saveAll(unsaved));
        return tagEntities;
    }

    @Override
    public TagEntity mapRequestToEntity(String name) {
        return new TagEntity(name);
    }

    @Override
    public TagResponse mapEntityToResponse(TagEntity entity) {
        return modelMapper.map(entity, TagResponse.class);
    }
}
