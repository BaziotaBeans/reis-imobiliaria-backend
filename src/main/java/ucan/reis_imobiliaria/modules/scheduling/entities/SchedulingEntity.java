package ucan.reis_imobiliaria.modules.scheduling.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.property.entities.PropertyScheduleEntity;
import ucan.reis_imobiliaria.modules.user.entities.User;

@Entity
@Table(name = "scheduling")
@Data
public class SchedulingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkScheduling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_property_schedule")
    private PropertyScheduleEntity propertySchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_property")
    private PropertyEntity property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String note; 

    @CreationTimestamp
    private LocalDateTime createdAt;
}
