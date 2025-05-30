package ucan.reis_imobiliaria.modules.property.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ucan.reis_imobiliaria.modules.property.utils.property.PropertyUtil;

@Data
public class UpdatePropertyStatusDTO {
    @NotNull(message = "O status da propriedade não pode ser nulo.")
    private PropertyUtil.PropertyStatus propertyStatus;
}
