package ucan.reis_imobiliaria.modules.property.dto;

import lombok.Data;

@Data
public class PropertyScheduleDTO {
    private String dayOfWeek;

    private String startTime;

    private String endTime;
}
