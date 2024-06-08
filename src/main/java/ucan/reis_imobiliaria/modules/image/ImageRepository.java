package ucan.reis_imobiliaria.modules.image;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ucan.reis_imobiliaria.modules.image.entities.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
    
}
