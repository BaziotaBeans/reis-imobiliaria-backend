package ucan.reis_imobiliaria.modules.property.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import ucan.reis_imobiliaria.modules.property.utils.property.PropertyUtil.PropertyStatus;

@Data
public class CreatePropertyDTO {
    private UUID pkProperty;

    private String title;

    private String province;

    private String county;

    private String address;

    private int suits;

    private int room;

    private int bathroom;

    private int vacancy;

    private double price;

    private double totalArea;

    private double buildingArea;

    private String description;

    private String paymentModality;

    private boolean status;

    private UUID fkCompany;

    private UUID fkPropertyType;

    private List<String> images;

    private PropertyStatus propertyStatus;

    private List<PropertyScheduleDTO> schedules;

    private double latitude;

    private double longitude;

    private String conservation;

    private String propertyType;

    private double condominiumFee;
}
