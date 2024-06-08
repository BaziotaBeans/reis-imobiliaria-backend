package ucan.reis_imobiliaria.modules.property.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "property_type")
public class PropertyTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkPropertyType;
    
    private String designation;
    
}
