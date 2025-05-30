package ucan.reis_imobiliaria.modules.property.useCases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import ucan.reis_imobiliaria.exceptions.ResourceNotFoundException;
import ucan.reis_imobiliaria.modules.property.PropertyRepository;
import ucan.reis_imobiliaria.modules.property.dto.PropertyAndImageDTO;
import ucan.reis_imobiliaria.modules.property.dto.PropertyImageDTO;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.utils.property.PropertyUtil.PropertyStatus;
import ucan.reis_imobiliaria.modules.scheduling.SchedulingRepository;

@Service
public class PropertyUseCase {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    public List<PropertyEntity> getPropertiesByComapny(UUID companyId) {
        return propertyRepository.findByCompanyEntityPkCompany(companyId);
    }

    @Transactional()
    public List<PropertyEntity> findAll() {
        return propertyRepository.findAll();
    }

    public PropertyEntity findById(UUID pkProperty) {
        Optional<PropertyEntity> propertyOptional = propertyRepository.findById(pkProperty);
        return propertyOptional
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrado com o Id" + pkProperty));
    }

    public List<PropertyImageDTO> findPropertiesAndImages() {
        return propertyRepository.findPropertiesAndImages();
    }

    public List<PropertyAndImageDTO> findAllPropertyAndImage(UUID pkCompany) {
        return propertyRepository.findAllPropertyAndImage(pkCompany);
    }

    public List<PropertyAndImageDTO> findAllPropertiesWithStatusTrue() {
        return propertyRepository.findAllPropertiesDataWithStatusTrue();
    }

    public PropertyAndImageDTO findByPropertyAndImageByPkProperty(UUID pkProperty) {
        return propertyRepository.findByPkProperty(pkProperty);
    }

    @Transactional
    public PropertyEntity updatePropertyStatus(UUID pkProperty, PropertyStatus status) {
        PropertyEntity property = propertyRepository.findById(pkProperty)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com o ID: " + pkProperty));

        // Verifica se o status é válido
        if (status == null) {
            throw new IllegalArgumentException("O status da propriedade não pode ser nulo.");
        }

        // 🚫 Se o imóvel já está RENTED, não pode ser alterado
        if (property.getPropertyStatus() == PropertyStatus.RENTED) {
            throw new IllegalStateException("Não é possível alterar o status de uma propriedade já alugada/comprado.");
        }

        // 🚫 Verifica se há agendamentos para essa propriedade
        boolean hasScheduling = schedulingRepository.existsByPropertyPkProperty(pkProperty);
        if (hasScheduling) {
            throw new IllegalStateException("Não é possível alterar o status de uma propriedade associada a um agendamento.");
        }

        property.setPropertyStatus(status);
        return propertyRepository.save(property);
    }

    // @Transactional
    public void delete(UUID pkProperty) {
        PropertyEntity propertyEntity = findById(pkProperty);

        // Verifica se o propertyStatus é diferente de RENTED
        if (!propertyEntity.getPropertyStatus().equals(PropertyStatus.RENTED)) {
            propertyEntity.setStatus(false);
            this.propertyRepository.save(propertyEntity);
        } else {
            // Opção para lidar com a tentativa de desativar uma propriedade RENTED
            // Pode ser lançando uma exceção, logar um aviso, etc.
            throw new IllegalStateException("Não é possível desativar a propriedade pois já está arrendada (RENTED).");
        }
    }
}
