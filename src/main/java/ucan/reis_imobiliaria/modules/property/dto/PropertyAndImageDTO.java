package ucan.reis_imobiliaria.modules.property.dto;

import java.util.List;

import lombok.Data;
import ucan.reis_imobiliaria.modules.image.entities.ImageEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;

@Data
public class PropertyAndImageDTO {
    private PropertyEntity property;
    private List<ImageEntity> images;
    private List<PropertyScheduleEntity> schedules;

    public PropertyAndImageDTO(PropertyEntity property, List<ImageEntity> images,
            List<PropertyScheduleEntity> schedules) {
        this.property = property;
        this.images = images;
        this.schedules = schedules;
    }

    public PropertyAndImageDTO(PropertyEntity property, List<ImageEntity> images) {
        this.property = property;
        this.images = images;
    }
}
