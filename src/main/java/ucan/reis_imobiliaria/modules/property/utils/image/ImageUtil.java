package ucan.reis_imobiliaria.modules.property.utils.image;

import ucan.reis_imobiliaria.modules.image.entities.ImageEntity;

public class ImageUtil {
    
    static public ImageEntity convertToEntity(String url) {
        ImageEntity imageEntity = new ImageEntity();

        imageEntity.setUrl(url);

        return imageEntity;
    }
}
