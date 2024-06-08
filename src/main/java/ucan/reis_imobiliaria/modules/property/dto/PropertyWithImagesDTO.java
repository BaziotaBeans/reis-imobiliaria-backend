package ucan.reis_imobiliaria.modules.property.dto;

import java.util.List;

import lombok.Data;
import ucan.reis_imobiliaria.modules.image.entities.ImageEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;

@Data
public class PropertyWithImagesDTO {
    private PropertyEntity property;
    private List<ImageEntity> images;
}
