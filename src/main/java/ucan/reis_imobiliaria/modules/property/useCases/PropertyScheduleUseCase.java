package ucan.reis_imobiliaria.modules.property.useCases;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ucan.reis_imobiliaria.modules.property.PropertyScheduleRepository;
import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;

@Service
public class PropertyScheduleUseCase {

    int todayDow = LocalDate.now().get(ChronoField.DAY_OF_WEEK);
    
    @Autowired
    private PropertyScheduleRepository propertyScheduleRepository;

    public List<PropertyScheduleEntity> getSchedulesByPropertyId(UUID propertyId) {
        return propertyScheduleRepository.findSchedulesByPropertyId(propertyId);
    }

    public List<PropertyScheduleEntity> getAvailableSchedulesByPropertyId(UUID propertyId) {
        return propertyScheduleRepository.findAvailableSchedulesByPropertyId(propertyId);
    }
    
    public List<PropertyScheduleEntity> getAvailableSchedules(UUID propertyId) {
        return propertyScheduleRepository.findAvailableSchedulesByPropertyId(propertyId);
    }
}
