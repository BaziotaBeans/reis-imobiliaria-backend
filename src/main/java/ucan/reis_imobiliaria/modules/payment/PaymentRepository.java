package ucan.reis_imobiliaria.modules.payment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ucan.reis_imobiliaria.modules.payment.entities.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    
}
