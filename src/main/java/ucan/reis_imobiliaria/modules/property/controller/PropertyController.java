package ucan.reis_imobiliaria.modules.property.controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ucan.reis_imobiliaria.exceptions.ResourceNotFoundException;
import ucan.reis_imobiliaria.modules.company.CompanyEntity;
import ucan.reis_imobiliaria.modules.company.CompanyRepository;
import ucan.reis_imobiliaria.modules.image.ImageRepository;
import ucan.reis_imobiliaria.modules.image.entities.ImageEntity;
import ucan.reis_imobiliaria.modules.property.PropertyRepository;
import ucan.reis_imobiliaria.modules.property.PropertyScheduleRepository;
import ucan.reis_imobiliaria.modules.property.PropertyTypeRepository;
import ucan.reis_imobiliaria.modules.property.dto.CreatePropertyDTO;
import ucan.reis_imobiliaria.modules.property.dto.PropertyAndImageDTO;
import ucan.reis_imobiliaria.modules.property.dto.PropertyScheduleDTO;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyTypeEntity;
import ucan.reis_imobiliaria.modules.property.useCases.PropertyUseCase;
import ucan.reis_imobiliaria.modules.property.utils.property.PropertyUtil;
import ucan.reis_imobiliaria.modules.propertyImage.PropertyImageRepository;
import ucan.reis_imobiliaria.modules.propertyImage.entities.PropertyImageEntity;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Autowired
    private PropertyScheduleRepository propertyScheduleRepository;

    @Autowired
    private PropertyUseCase propertyUseCase;

    @Autowired
    private PropertyImageRepository propertyImageRepository;

    @PreAuthorize("hasRole('COMPANY')")
    @GetMapping("/")
    public ResponseEntity<List<PropertyEntity>> findAll(@RequestParam(required = false) String title) {
        List<PropertyEntity> properties = new ArrayList<PropertyEntity>();

        // propertyUseCase.findAllWithDetails().forEach(properties::add);

        propertyUseCase.findAll().forEach(properties::add);

        // properties.forEach(property -> {
        // System.out.println(property.getImages());
        // });

        // for (PropertyEntity property : properties) {
        // property.getImages().size(); // Isso forçará o carregamento das imagens
        // System.out.println(property.getImages().size());
        // }

        // if (title == null) {
        // properyRepository.findAll().forEach(properties::add);
        // } else {
        // properyRepository.findByTitleContaining(title).forEach(properties::add);
        // }

        if (properties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/with-status-true")
    public ResponseEntity<?> findAllPropertiesWithStatusTrue() {
        List<PropertyAndImageDTO> properties = new ArrayList<PropertyAndImageDTO>();

        propertyUseCase.findAllPropertiesWithStatusTrue().forEach(properties::add);

        if (properties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> findPropertiesAndImages(@PathVariable("id") UUID pkCompany) {
        List<PropertyAndImageDTO> properties = new ArrayList<PropertyAndImageDTO>();

        propertyUseCase.findAllPropertyAndImage(pkCompany).forEach(properties::add);

        if (properties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('COMPANY')")
    @GetMapping("/{id}")
    public ResponseEntity<PropertyEntity> findById(@PathVariable("id") UUID pkProperty) {
        PropertyEntity property = propertyRepository.findById(pkProperty)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + pkProperty));

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> create(@RequestBody CreatePropertyDTO createPropertyDTO) {
        try {
            PropertyEntity propertyEntity = PropertyUtil.convertToEntity(createPropertyDTO);

            CompanyEntity companyEntity = companyRepository.findById(createPropertyDTO.getFkCompany())
                    .orElseThrow(() -> new IllegalArgumentException("Company not found"));

            propertyEntity.setCompanyEntity(companyEntity);

            PropertyTypeEntity propertyTypeEntity = propertyTypeRepository
                    .findById(createPropertyDTO.getFkPropertyType())
                    .orElseThrow(() -> new IllegalArgumentException("PropertyType not found"));

            propertyEntity.setFkPropertyTypeEntity(propertyTypeEntity);

            PropertyEntity createdProperty = propertyRepository.save(propertyEntity);

            createPropertyDTO.getImages().forEach(image -> {
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setUrl(image);
                ImageEntity createdImage = imageRepository.save(imageEntity);

                PropertyImageEntity propertyImageEntity = new PropertyImageEntity(createdProperty, createdImage);
                propertyImageRepository.save(propertyImageEntity);

            });

            for (PropertyScheduleDTO propertyScheduleDTO : createPropertyDTO.getSchedules()) {
                PropertyScheduleEntity propertyScheduleEntity = new PropertyScheduleEntity();
                propertyScheduleEntity.setDayOfWeek(DayOfWeek.valueOf(propertyScheduleDTO.getDayOfWeek()));
                propertyScheduleEntity.setStartTime(LocalTime.parse(propertyScheduleDTO.getStartTime()));
                propertyScheduleEntity.setEndTime(LocalTime.parse(propertyScheduleDTO.getEndTime()));
                propertyScheduleEntity.setProperty(createdProperty);

                propertyScheduleRepository.save(propertyScheduleEntity);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar a propriedade: " + e.getMessage());
        }
    }

    @DeleteMapping("/{pkProperty}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> delete(@PathVariable UUID pkProperty) {
        try {
            propertyUseCase.delete(pkProperty);
            return ResponseEntity.ok("Propridade removido com sucesso");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover a propriedade \n" + e.getMessage());
        }
    }

}
