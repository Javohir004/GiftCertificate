package uz.pdp.giftcertificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.giftcertificate.domain.entity.TagEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Repository
public interface TagRepository extends JpaRepository<TagEntity, UUID> {


    Set<TagEntity> findAllByNameIn(Set<String> tagNames);

    List<TagEntity> findAllByNameNotIn(List<String> tagNames);


}
