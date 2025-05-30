package ucan.reis_imobiliaria.modules.property.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

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

@Entity
@Table(name = "propertySchedule")
@Data
public class PropertyScheduleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkPropertySchedule;

    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(name = "status", nullable = false)
    private String status = "AVAILABLE"; // NEW ATTRIBUTE (e.g., AVAILABLE, UNAVAILABLE)

    // Relacionamento bidirecional com PropertyEntity (se necessário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_property")
    private PropertyEntity property;
}   
