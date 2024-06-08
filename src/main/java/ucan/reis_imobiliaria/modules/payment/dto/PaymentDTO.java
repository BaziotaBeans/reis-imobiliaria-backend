package ucan.reis_imobiliaria.modules.payment.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private String reference;
    private Double totalValue;
}
