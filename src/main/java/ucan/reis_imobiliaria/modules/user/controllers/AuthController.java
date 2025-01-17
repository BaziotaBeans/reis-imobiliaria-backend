package ucan.reis_imobiliaria.modules.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucan.reis_imobiliaria.modules.user.entities.ERole;
import ucan.reis_imobiliaria.modules.user.entities.Role;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.payload.request.LoginRequest;
import ucan.reis_imobiliaria.modules.user.payload.request.SignupRequest;
import ucan.reis_imobiliaria.modules.user.payload.response.JwtResponse;
import ucan.reis_imobiliaria.modules.user.payload.response.MessageResponse;
import ucan.reis_imobiliaria.modules.user.payload.response.SignUpResponse;
import ucan.reis_imobiliaria.modules.user.repository.RoleRepository;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;
import ucan.reis_imobiliaria.modules.user.security.jwt.JwtUtils;
import ucan.reis_imobiliaria.modules.user.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        UserRepository userRepository;

        @Autowired
        RoleRepository roleRepository;

        @Autowired
        PasswordEncoder encoder;

        @Autowired
        JwtUtils jwtUtils;

        private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

        @PostMapping("/signin")
        public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);
                Date expirationDate = jwtUtils.getExpirationDateFromJwtToken(jwt);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                                .map(item -> item.getAuthority())
                                .collect(Collectors.toList());

                return ResponseEntity.ok(new JwtResponse(jwt,
                                userDetails.getPkUser(),
                                userDetails.getUsername(),
                                userDetails.getFullName(),
                                userDetails.getEmail(),
                                userDetails.getPhone(),
                                roles,
                                userDetails.getNif(),
                                userDetails.getAddress(),
                                userDetails.getNationality(),
                                userDetails.getMaritalStatus(),
                                expirationDate));
        }

        @PostMapping("/signup")
        public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
                if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(new MessageResponse("Erro: O nome de usuário já está em uso!"));
                }

                if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(new MessageResponse("Erro: O e-mail já está em uso!"));
                }

                if (userRepository.existsByPhone(signUpRequest.getPhone())) {
                        return ResponseEntity
                                        .badRequest()
                                        .body(new MessageResponse("Erro: O número de telefone já está em uso!"));
                }
                

                // Create new user's account
                User user = new User(signUpRequest.getUsername(),
                                signUpRequest.getFullName(),
                                signUpRequest.getEmail(),
                                encoder.encode(signUpRequest.getPassword()),
                                signUpRequest.getPhone(),
                                signUpRequest.getAddress(),
                                signUpRequest.getNif(),
                                signUpRequest.getNationality(),
                                signUpRequest.getMaritalStatus(),
                                signUpRequest.getUrlDocument()
                                );

                Set<String> strRoles = signUpRequest.getRole();
                Set<Role> roles = new HashSet<>();

                if (strRoles == null) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                } else {
                        strRoles.forEach(role -> {
                                switch (role) {
                                        case "admin":
                                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error: Role is not found."));
                                                roles.add(adminRole);

                                                break;
                                        case "company":
                                                Role compRole = roleRepository.findByName(ERole.ROLE_COMPANY)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error: Role is not found."));
                                                roles.add(compRole);

                                                break;
                                        default:
                                                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error: Role is not found."));
                                                roles.add(userRole);
                                }
                        });
                }

                user.setRoles(roles);
                userRepository.save(user);

                return ResponseEntity.ok(new SignUpResponse(
                                user.getPkUser(),
                                user.getUsername(),
                                user.getFullName(),
                                user.getEmail(),
                                user.getPhone(),
                                user.getNif(),
                                user.getAddress(),
                                user.getNationality(),
                                user.getMaritalStatus(),
                                user.getUrlDocument(),
                                "User registered successfully!"));
        }
}
