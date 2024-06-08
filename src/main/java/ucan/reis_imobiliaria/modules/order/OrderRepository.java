package ucan.reis_imobiliaria.modules.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ucan.reis_imobiliaria.modules.order.entities.OrderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository <OrderEntity, UUID>{

    @Query("SELECT o FROM OrderEntity o WHERE o.user.pkUser = :userId")
    List<OrderEntity> findByUserId(UUID userId);

    @Query("SELECT o FROM OrderEntity o WHERE o.property.pkProperty = :propertyId")
    List<OrderEntity> findByPropertyId(UUID propertyId);

    @Query(value = "SELECT * FROM orders ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<OrderEntity> findLastOrder();

    Optional<OrderEntity> findByReference(String reference);
}