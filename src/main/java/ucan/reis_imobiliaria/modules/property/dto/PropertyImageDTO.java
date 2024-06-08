package ucan.reis_imobiliaria.modules.property.dto;

import lombok.Data;
import ucan.reis_imobiliaria.modules.image.entities.ImageEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.propertyImage.entities.PropertyImageEntity;


@Data
public class PropertyImageDTO {
    private PropertyEntity property;
    private PropertyImageEntity propertyImage;
    private ImageEntity image;


    public PropertyImageDTO(PropertyEntity property, PropertyImageEntity propertyImage, ImageEntity image) {
        this.property = property;
        this.propertyImage = propertyImage;
        this.image = image;
    }
}
