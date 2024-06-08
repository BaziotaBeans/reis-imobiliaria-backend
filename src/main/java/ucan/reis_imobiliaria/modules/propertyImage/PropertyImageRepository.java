package ucan.reis_imobiliaria.modules.propertyImage;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ucan.reis_imobiliaria.modules.propertyImage.entities.PropertyImageEntity;

public interface PropertyImageRepository extends JpaRepository<PropertyImageEntity, UUID> {

}
