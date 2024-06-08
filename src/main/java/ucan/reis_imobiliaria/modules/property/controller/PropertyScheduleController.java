package ucan.reis_imobiliaria.modules.property.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;
import ucan.reis_imobiliaria.modules.property.useCases.PropertyScheduleUseCase;

@RestController
@RequestMapping("/api/property/schedule")
public class PropertyScheduleController {
    
    @Autowired
    PropertyScheduleUseCase propertyScheduleUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<?> findSchedulesByPropertyId(@PathVariable("id") UUID pkProperty) {
        List<PropertyScheduleEntity> schedules = new ArrayList<PropertyScheduleEntity>();

        propertyScheduleUseCase.getSchedulesByPropertyId(pkProperty).forEach(schedules::add);

        if (schedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }


    @GetMapping("/available/{id}")
    public ResponseEntity<?> findAvailableSchedulesByPropertyId(@PathVariable("id") UUID pkProperty) {
        List<PropertyScheduleEntity> schedules = new ArrayList<PropertyScheduleEntity>();

        propertyScheduleUseCase.getAvailableSchedules(pkProperty).forEach(schedules::add);

        if (schedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    
}
