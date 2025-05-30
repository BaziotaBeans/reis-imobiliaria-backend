package ucan.reis_imobiliaria.modules.schedulingPaymentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.schedulingPaymentEntity.entities.SchedulingPaymentEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchedulingPaymentRepository extends JpaRepository<SchedulingPaymentEntity, UUID> {
    List<SchedulingPaymentEntity> findByUserPkUser(UUID userId);

    Optional<SchedulingPaymentEntity> findTopByOrderByCreatedAtDesc();

    List<SchedulingPaymentEntity> findByScheduledDateAndPropertyAndScheduleDetails(
            LocalDate scheduledDate,
            PropertyEntity property,
            String scheduleDetails
    );

    @Query("SELECT sp FROM SchedulingPaymentEntity sp " +
            "JOIN sp.property p " +
            "JOIN p.companyEntity c " +
            "JOIN c.user u " +
            "WHERE u.pkUser = :userId")
    List<SchedulingPaymentEntity> findByCompanyUserId(UUID userId);
}