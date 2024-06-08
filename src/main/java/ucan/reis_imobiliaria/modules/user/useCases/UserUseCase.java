package ucan.reis_imobiliaria.modules.user.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ucan.reis_imobiliaria.exceptions.ResourceNotFoundException;
import ucan.reis_imobiliaria.exceptions.UserFoundException;
import ucan.reis_imobiliaria.modules.user.dto.UserDTO;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

@Service
public class UserUseCase {
    @Autowired
    private UserRepository userRepository;

    public User findById(UUID pkUser) {
        Optional<User> userOptional = userRepository.findById(pkUser);
        return userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User update(UserDTO userDTO, UUID pkUser) {
        User user = findById(pkUser);

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setNif(userDTO.getNif());
        user.setNationality(userDTO.getNationality());
        user.setMaritalStatus(userDTO.getMaritalStatus());

        return userRepository.save(user);
    }
}
