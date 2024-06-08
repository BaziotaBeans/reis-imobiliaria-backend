package ucan.reis_imobiliaria.modules.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import ucan.reis_imobiliaria.modules.image.entities.ImageEntity;
import ucan.reis_imobiliaria.modules.property.dto.PropertyAndImageDTO;
import ucan.reis_imobiliaria.modules.property.dto.PropertyImageDTO;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;

@Transactional
public interface PropertyRepository extends JpaRepository<PropertyEntity, UUID> {
    // List<PropertyEntity> findPropertyByImagesPkImage(UUID pkImage);

    List<PropertyEntity> findByTitleContaining(String title);

    List<PropertyEntity> findByCompanyEntityPkCompany(UUID pkCompany);

    @Override
    // @Query("SELECT DISTINCT p FROM PropertyEntity p JOIN FETCH p.propertyImages
    // pi JOIN FETCH pi.image")
    List<PropertyEntity> findAll();

    @Query("SELECT DISTINCT new ucan.reis_imobiliaria.modules.property.dto.PropertyImageDTO(p, pi, i) FROM PropertyEntity p JOIN FETCH p.propertyImages pi JOIN FETCH pi.image i")
    List<PropertyImageDTO> findPropertiesAndImages();

    @Query("SELECT p, i FROM PropertyEntity p JOIN FETCH p.propertyImages pi JOIN FETCH pi.image i WHERE p.status = true AND p.propertyStatus = 'PUBLISHED'")
    List<Object[]> findAllPropertiesWithStatusTrue();

    default List<PropertyAndImageDTO> findAllPropertiesDataWithStatusTrue() {
        List<Object[]> results = findAllPropertiesWithStatusTrue();

        Map<PropertyEntity, List<ImageEntity>> propertyImageMap = new HashMap();

        for (Object[] result : results) {
            PropertyEntity property = (PropertyEntity) result[0];

            ImageEntity image = (ImageEntity) result[1];

            propertyImageMap.computeIfAbsent(property, k -> new ArrayList<>()).add(image);
        }

        List<PropertyAndImageDTO> dtos = new ArrayList<>();

        propertyImageMap.forEach((property, images) -> dtos.add(new PropertyAndImageDTO(property, images)));

        return dtos;
    }

    @Query("SELECT p, i FROM PropertyEntity p JOIN FETCH p.propertyImages pi JOIN FETCH pi.image i WHERE p.fkCompany = :pkCompany AND p.status = true")
    List<Object[]> findPropertyAndImageMappings(UUID pkCompany);

    default List<PropertyAndImageDTO> findAllPropertyAndImage(UUID pkCompany) {
        List<Object[]> results = findPropertyAndImageMappings(pkCompany);

        Map<PropertyEntity, List<ImageEntity>> propertyImageMap = new HashMap();

        for (Object[] result : results) {
            PropertyEntity property = (PropertyEntity) result[0];

            ImageEntity image = (ImageEntity) result[1];

            propertyImageMap.computeIfAbsent(property, k -> new ArrayList<>()).add(image);
        }

        List<PropertyAndImageDTO> dtos = new ArrayList<>();

        propertyImageMap.forEach((property, images) -> dtos.add(new PropertyAndImageDTO(property, images)));

        return dtos;

    }
}
