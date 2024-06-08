package ucan.reis_imobiliaria.modules.order.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID userId;
    private UUID propertyId;
    private String entidade;
    private double totalValue;
}
