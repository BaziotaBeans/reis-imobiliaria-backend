package ucan.reis_imobiliaria.modules.schedulingPaymentEntity.dto;

import lombok.Data;
import ucan.reis_imobiliaria.modules.payment.utils.PaymentUtils;

import java.util.UUID;


@Data
public class PaymentRequestDTO {
    private UUID userId; // ID do usuário que faz o pagamento
    private UUID propertyId; // ID do imóvel associado
    private UUID schedulingId; // ID do agendamento associado
    private Double totalValue; // Valor total do pagamento
    private PaymentUtils.PaymentMethod paymentMethod; // Método de pagamento
    private String reference; // Referência do pagamento (opcional)
}
