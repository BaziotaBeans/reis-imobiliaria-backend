package ucan.reis_imobiliaria.modules.schedulingPaymentEntity.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import ucan.reis_imobiliaria.modules.payment.utils.PaymentUtils;
import ucan.reis_imobiliaria.modules.scheduling.entities.SchedulingEntity;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;

@Entity
@Table(name = "scheduling_payments")
@Data
public class SchedulingPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkSchedulingPayment;

    // Referência ao usuário que realizou o pagamento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    // Referência ao imóvel agendado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_property", nullable = false)
    private PropertyEntity property;

    // Informações do agendamento
    @Column(nullable = false)
    private LocalDate scheduledDate; // Data agendada

    @Column(nullable = false)
    private String scheduleDetails; // Detalhes do agendamento (serializado ou resumo)

    // Informações do pagamento
    @Column(nullable = false)
    private Double totalValue; // Valor total do pagamento

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentUtils.PaymentMethod paymentMethod; // Método de pagamento

    @Column(nullable = false)
    private LocalDateTime createdAt; // Data de criação do pagamento

    @Column
    private String reference; // Referência opcional para o pagamento
}
