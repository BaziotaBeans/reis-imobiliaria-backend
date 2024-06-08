package ucan.reis_imobiliaria.modules.client;

import java.util.Optional;
import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID>{
   // Optional<ClientEntity> findByPhone(String phone);
   Optional<ClientEntity> findByNif(String nif);
}
