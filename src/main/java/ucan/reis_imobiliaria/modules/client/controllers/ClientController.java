package ucan.reis_imobiliaria.modules.client.controllers;

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
import ucan.reis_imobiliaria.modules.client.ClientEntity;
import ucan.reis_imobiliaria.modules.client.useCases.CreateClientUseCase;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateClientUseCase createClientUseCase;

    @PostMapping("/{userId}")
    public ResponseEntity<Object> create(@PathVariable(value = "userId") UUID userId,
            @Valid @RequestBody ClientEntity clientEntity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userId));
        
        clientEntity.setUser(user);

        try {
            var result = this.createClientUseCase.execute(clientEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
