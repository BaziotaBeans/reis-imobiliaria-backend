package ucan.reis_imobiliaria.modules.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ucan.reis_imobiliaria.modules.user.entities.ERole;
import ucan.reis_imobiliaria.modules.user.entities.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(ERole name);
}
