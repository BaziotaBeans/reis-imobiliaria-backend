package ucan.reis_imobiliaria.modules.scheduling;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ucan.reis_imobiliaria.modules.scheduling.entities.SchedulingEntity;
// import ucan.reis_imobiliaria.modules.user.entities.User;

public interface SchedulingRepository extends JpaRepository<SchedulingEntity, UUID>{
    @Query("SELECT COUNT(s) > 0 FROM SchedulingEntity s WHERE s.propertySchedule.pkPropertySchedule = :pkPropertySchedule AND FUNCTION('DATE', s.createdAt) = :date")
    boolean existsByPropertyScheduleIdAndCreatedAt(UUID pkPropertySchedule, LocalDate date);

    @Query("SELECT s FROM SchedulingEntity s WHERE s.user.pkUser = :pkUser")
    List<SchedulingEntity> findByUserPkUser(UUID pkUser);

    @Query("SELECT s FROM SchedulingEntity s JOIN s.property p WHERE p.fkCompany = :companyId")
    List<SchedulingEntity> findByCompany(UUID companyId);
}
