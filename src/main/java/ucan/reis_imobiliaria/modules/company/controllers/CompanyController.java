package ucan.reis_imobiliaria.modules.company.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ucan.reis_imobiliaria.exceptions.ResourceNotFoundException;
import ucan.reis_imobiliaria.modules.company.CompanyEntity;
import ucan.reis_imobiliaria.modules.company.useCases.CreateCompanyUseCase;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/{userId}")
    public ResponseEntity<Object> create(@PathVariable(value = "userId") UUID userId,
            @Valid @RequestBody CompanyEntity companyEntity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userId));

        companyEntity.setUser(user);

        try {
            var result = this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
