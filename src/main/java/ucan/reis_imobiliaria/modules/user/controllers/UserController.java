package ucan.reis_imobiliaria.modules.user.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import ucan.reis_imobiliaria.modules.user.dto.ChangePasswordDTO;
import ucan.reis_imobiliaria.modules.user.dto.UserDTO;
import ucan.reis_imobiliaria.modules.user.dto.UserUpdateDTO;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;
import ucan.reis_imobiliaria.modules.user.useCases.UserUseCase;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUseCase userUseCase;

    @GetMapping("/findByLoggedUser")
    public User findByLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();
        
        Optional<User> userOptional = userRepository.findByUsername(username);

        User user = userOptional.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        return user;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @Validated @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            return ResponseEntity.ok(userUseCase.updateUser(id, userUpdateDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable UUID id, @Validated @RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            userUseCase.changePassword(id, changePasswordDTO);
            return ResponseEntity.ok("Password changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userUseCase.update(userDTO, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
