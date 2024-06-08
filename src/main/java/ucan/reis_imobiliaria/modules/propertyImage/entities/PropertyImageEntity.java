package ucan.reis_imobiliaria.modules.propertyImage.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ucan.reis_imobiliaria.modules.image.entities.ImageEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;

@Data
@Entity
@Table(name = "property_image")

public class PropertyImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkPropertyImage;

    @JsonManagedReference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_property")
    private PropertyEntity propertyImages; // Chave estrangeira para PropertyEntity

    @JsonManagedReference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_image")
    private ImageEntity image; // Chave estrangeira para ImageEntity

    public PropertyImageEntity() {
    }

    public PropertyImageEntity(PropertyEntity property, ImageEntity image) {
        this.propertyImages = property;
        this.image = image;
    }
}
