package ucan.reis_imobiliaria.modules.property.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ucan.reis_imobiliaria.modules.property.PropertyTypeRepository;
import ucan.reis_imobiliaria.modules.property.entities.PropertyTypeEntity;

@RestController
@RequestMapping("/api/property-type")
public class PropertyTypeController {
    
    @Autowired
    PropertyTypeRepository propertyTypeRepository;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<PropertyTypeEntity > propertyTypes = new ArrayList<PropertyTypeEntity>();

        propertyTypeRepository.findAll().forEach(propertyTypes::add);

        if (propertyTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(propertyTypes, HttpStatus.OK);
    }
}
