package ucan.reis_imobiliaria.modules.property;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ucan.reis_imobiliaria.modules.property.entities.PropertyTypeEntity;

public interface PropertyTypeRepository extends JpaRepository<PropertyTypeEntity, UUID>{
    
}
