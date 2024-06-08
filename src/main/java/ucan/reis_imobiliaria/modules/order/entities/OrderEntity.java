package ucan.reis_imobiliaria.modules.order.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ucan.reis_imobiliaria.modules.order.utils.OrderUtil.OrderState;
import ucan.reis_imobiliaria.modules.property.entities.PropertyEntity;
import ucan.reis_imobiliaria.modules.user.entities.User;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID pkOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User user;

    @Column(unique = true)
    private String reference;

    private LocalDateTime expirationDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String entidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_property")
    private PropertyEntity property;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderState orderState;

    private double totalValue;
}
