package ucan.reis_imobiliaria.modules.payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucan.reis_imobiliaria.modules.payment.entities.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    Optional<PaymentEntity> findByReference(String reference);

    @Query(value = "SELECT * FROM payments ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<PaymentEntity> findLastPayment();

    @Query("SELECT payment FROM PaymentEntity payment " +
            "JOIN payment.property property " +
            "JOIN property.companyEntity company " +
            "JOIN company.user user " +
            "WHERE user.pkUser = :userId")
    List<PaymentEntity> findByCompanyUserId(UUID userId);
}