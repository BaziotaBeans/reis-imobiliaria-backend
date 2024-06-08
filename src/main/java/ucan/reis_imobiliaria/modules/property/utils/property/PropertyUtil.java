package ucan.reis_imobiliaria.modules.property.utils.property;

import ucan.reis_imobiliaria.modules.property.dto.CreatePropertyDTO;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;

public class PropertyUtil {

    static public PropertyEntity convertToEntity(CreatePropertyDTO propertyDTO) {

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setTitle(propertyDTO.getTitle());
        propertyEntity.setProvince(propertyDTO.getProvince());
        propertyEntity.setCounty(propertyDTO.getCounty());
        propertyEntity.setAddress(propertyDTO.getAddress());
        propertyEntity.setSuits(propertyDTO.getSuits());
        propertyEntity.setRoom(propertyDTO.getRoom());
        propertyEntity.setBathroom(propertyDTO.getBathroom());
        propertyEntity.setVacancy(propertyDTO.getVacancy());
        propertyEntity.setPrice(propertyDTO.getPrice());
        propertyEntity.setTotalArea(propertyDTO.getTotalArea());
        propertyEntity.setBuildingArea(propertyDTO.getBuildingArea());
        propertyEntity.setDescription(propertyDTO.getDescription());
        propertyEntity.setPaymentModality(propertyDTO.getPaymentModality());
        propertyEntity.setStatus(propertyDTO.isStatus());
        propertyEntity.setFkCompany(propertyDTO.getFkCompany());
        propertyEntity.setFkPropertyType(propertyDTO.getFkPropertyType());
        propertyEntity.setPropertyStatus(propertyDTO.getPropertyStatus());

        return propertyEntity;
    }

    static public enum PropertyStatus {
        PUBLISHED, // Disponível para ser agendado para visitas
        STANDBY, // Aguardando ser publicado ou disponibilizado para visitas
        RENTED // Já foi arrendado, não está disponível para novas visitas
    }
}
