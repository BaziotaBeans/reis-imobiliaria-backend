package ucan.reis_imobiliaria.modules.order.dto;

import lombok.Data;
import ucan.reis_imobiliaria.modules.payment.utils.PaymentUtils.PaymentMethod;

import java.util.UUID;

@Data
public class OrderDTO {
    private UUID userId;
    private UUID propertyId;
    private String entidade;
    private double totalValue;
    private PaymentMethod paymentMethod;
}
