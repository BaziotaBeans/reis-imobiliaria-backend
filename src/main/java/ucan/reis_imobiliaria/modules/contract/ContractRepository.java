package ucan.reis_imobiliaria.modules.contract;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ucan.reis_imobiliaria.modules.contract.entities.ContractEntity;

public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {
    @Query("SELECT c FROM ContractEntity c WHERE c.user.pkUser = :userId")
    List<ContractEntity> findContractsByUserId(@Param("userId") UUID userId);

    @Query("SELECT c FROM ContractEntity c WHERE c.property.fkCompany = :companyId")
    List<ContractEntity> findByCompany(UUID companyId);
}
