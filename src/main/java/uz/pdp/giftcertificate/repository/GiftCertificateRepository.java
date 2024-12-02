package uz.pdp.giftcertificate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.giftcertificate.domain.entity.GiftCertificateEntity;
import uz.pdp.giftcertificate.domain.entity.TagEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificateEntity, UUID> {
    List<GiftCertificateEntity> findAllByNameContainingAndDescriptionContaining(String name, String description);

    @Query(value = """
            select gc from GiftCertificateEntity gc 
            join gc.tags t on t.name in (:tags)
            where (gc.name ilike '%' || :word || '%' or gc.description ilike '%' || :word || '%')
            group by gc.id
            having count(t) = :tagCount
            """)
//            "and (gc.name ilike '%' || :word || '%' or gc.description like '%' || :word || '%')")
    Page<GiftCertificateEntity> filter(
            @Param(value = "tags") Set<String> tags,
            @Param(value = "tagCount") int tagCount,
            @Param(value = "word") String word,
            Pageable pageable);

    @Query(value = """
            select gc from GiftCertificateEntity gc 
            where (gc.name ilike '%' || :word || '%' or gc.description ilike '%' || :word || '%')
            """)
//            "and (gc.name ilike '%' || :word || '%' or gc.description like '%' || :word || '%')")
    Page<GiftCertificateEntity> filterNoTag(@Param(value = "word") String word, Pageable pageable);





    @Query(nativeQuery = true, value = "select gc.* from gift_certificate_entity gc where " +
            "(select count(t) from tag_entity t join gift_certificate_entity_tags ct on t.id = ct.tags_id " +
            "         where ct.gift_certificate_entity_id = gc.id and t.name in (:tags)) = :tagCount and" +
            " (gc.name ilike '%' || :word || '%' or gc.description ilike '%' || :word || '%')")
    Page<GiftCertificateEntity> filterNativeQuery(
            @Param(value = "tags") Set<String> tags,
            @Param(value = "tagCount") int tagCount,
            @Param(value = "word") String word,
            Pageable pageable);
}
