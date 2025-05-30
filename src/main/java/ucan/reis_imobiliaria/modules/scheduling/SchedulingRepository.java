package ucan.reis_imobiliaria.modules.scheduling;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ucan.reis_imobiliaria.modules.scheduling.entities.SchedulingEntity;
// import ucan.reis_imobiliaria.modules.user.entities.User;

public interface SchedulingRepository extends JpaRepository<SchedulingEntity, UUID>{
//    @Query("SELECT COUNT(s) > 0 FROM SchedulingEntity s WHERE s.propertySchedule.pkPropertySchedule = :pkPropertySchedule AND FUNCTION('DATE', s.createdAt) = :date")
//    boolean existsByPropertyScheduleIdAndCreatedAt(UUID pkPropertySchedule, LocalDate date);

    @Query("SELECT COUNT(s) > 0 FROM SchedulingEntity s WHERE s.propertySchedule.pkPropertySchedule = :pkPropertySchedule AND s.scheduledDate = :scheduledDate")
    boolean existsByPropertyScheduleIdAndScheduledDate(UUID pkPropertySchedule, LocalDate scheduledDate);

    @Query("SELECT s FROM SchedulingEntity s WHERE s.user.pkUser = :pkUser")
    List<SchedulingEntity> findByUserPkUser(UUID pkUser);

    @Query("SELECT s FROM SchedulingEntity s JOIN s.property p WHERE p.fkCompany = :companyId")
    List<SchedulingEntity> findByCompany(UUID companyId);

    @Query("SELECT s FROM SchedulingEntity s WHERE FUNCTION('DATE', s.createdAt) < :date")
    List<SchedulingEntity> findAllByCreatedAtBefore(LocalDate date);

    @Query("SELECT s FROM SchedulingEntity s WHERE s.scheduledDate < :currentDate")
    List<SchedulingEntity> findAllByScheduledDateBefore(LocalDate currentDate);

    @Query("SELECT s FROM SchedulingEntity s ORDER BY s.createdAt DESC LIMIT 1")
    SchedulingEntity findLastScheduling();

    // Retorna verdadeiro se existir pelo menos um agendamento para a propriedade
    boolean existsByPropertyPkProperty(UUID pkProperty);
}