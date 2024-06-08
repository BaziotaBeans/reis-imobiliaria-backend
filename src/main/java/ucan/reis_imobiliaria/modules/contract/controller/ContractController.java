package ucan.reis_imobiliaria.modules.contract.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ucan.reis_imobiliaria.modules.contract.ContractRepository;
import ucan.reis_imobiliaria.modules.contract.entities.ContractEntity;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    @Autowired
    private ContractRepository contractRepository;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ContractEntity> contracts = new ArrayList<ContractEntity>();

        contractRepository.findAll().forEach(contracts::add);

        if (contracts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable("userId") UUID userId) {
        List<ContractEntity> contracts = new ArrayList<ContractEntity>();

        contractRepository.findContractsByUserId(userId).forEach(contracts::add);

        if (contracts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> findByCompanyId(@PathVariable("companyId") UUID companyId) {
        List<ContractEntity> contracts = new ArrayList<ContractEntity>(); 

        contractRepository.findByCompany(companyId).forEach(contracts::add);

        if (contracts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(contracts, HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractEntity> findById(@PathVariable("id") UUID id) {
        Optional<ContractEntity> contract = contractRepository.findById(id);
        return contract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
