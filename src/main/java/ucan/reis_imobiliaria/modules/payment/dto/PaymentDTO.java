package ucan.reis_imobiliaria.modules.payment.dto;

import lombok.Data;
import ucan.reis_imobiliaria.modules.payment.utils.PaymentUtils.PaymentMethod;

@Data
public class PaymentDTO {
    private String reference;
    private Double totalValue;
    private PaymentMethod paymentMethod; 
}
