package ucan.reis_imobiliaria.modules.property;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;

public interface PropertyScheduleRepository extends JpaRepository<PropertyScheduleEntity, UUID> {
    @Query("SELECT pse FROM PropertyScheduleEntity pse WHERE pse.property.pkProperty = :pkProperty")
    List<PropertyScheduleEntity> findSchedulesByPropertyId(UUID pkProperty);

    // @Query("SELECT pse FROM PropertyScheduleEntity pse WHERE
    // pse.property.pkProperty = :pkProperty AND NOT EXISTS (SELECT se FROM
    // SchedulingEntity se WHERE se.propertySchedule.pkPropertySchedule =
    // pse.pkPropertySchedule AND FUNCTION('DATE', se.createdAt) = CURRENT_DATE)")
    // @Query("SELECT pse FROM PropertyScheduleEntity pse WHERE pse.property.pkProperty = :pkProperty AND pse.pkPropertySchedule NOT IN (SELECT s.propertySchedule.pkPropertySchedule FROM SchedulingEntity s WHERE DATE(s.createdAt) = CURRENT_DATE) AND pse.dayOfWeek <> FUNCTION('DAYOFWEEK', CURRENT_DATE) - 1")
    
    @Query(value = "SELECT pse.* FROM property_schedule pse WHERE pse.fk_property = :pkProperty AND pse.status = 'AVAILABLE' AND NOT EXISTS (SELECT 1 FROM scheduling s WHERE s.fk_property_schedule = pse.pk_property_schedule AND EXTRACT(DOW FROM s.scheduled_date) = EXTRACT(DOW FROM CURRENT_DATE))", nativeQuery = true)
    
    // @Query(value = "SELECT pse.* FROM property_schedule pse WHERE pse.fk_property = :pkProperty AND NOT EXISTS (SELECT 1 FROM scheduling s WHERE s.fk_property_schedule = pse.pk_property_schedule AND EXTRACT(DOW FROM s.created_at) = EXTRACT(DOW FROM CURRENT_DATE)) AND (EXTRACT(DOW FROM CURRENT_DATE) <> pse.day_of_week OR CURRENT_TIME > pse.end_time)", nativeQuery = true)
    
    // @Query(value = "SELECT pse.* FROM property_schedule pse WHERE pse.fk_property = :pkProperty AND NOT EXISTS (SELECT 1 FROM scheduling s WHERE s.fk_property_schedule = pse.pk_property_schedule AND pse.day_of_week <> EXTRACT(DOW FROM CURRENT_DATE) AND CURRENT_TIME < pse.end_time)", nativeQuery = true)
        
    //AND (EXTRACT(DOW FROM CURRENT_DATE) <> pse.day_of_week OR CURRENT_TIME > pse.end_time)

    // @Query(value = "SELECT pse.* FROM property_schedule pse WHERE pse.fk_property = :pkProperty AND pse.day_of_week <> EXTRACT(DOW FROM CURRENT_DATE) OR pse.end_time < CURRENT_TIME", nativeQuery = true)
    List<PropertyScheduleEntity> findAvailableSchedulesByPropertyId(UUID pkProperty);

    @Query(value = "SELECT * FROM property_schedule pse WHERE pse.fk_property = :pkProperty AND pse.pk_property_schedule NOT IN (SELECT s.fk_property_schedule FROM scheduling s WHERE CAST(s.created_at AS date) = CURRENT_DATE) AND EXTRACT(DOW FROM CURRENT_DATE) <> :todayDow", nativeQuery = true)
    List<PropertyScheduleEntity> findAvailableSchedules(UUID pkProperty, int todayDow);

}
