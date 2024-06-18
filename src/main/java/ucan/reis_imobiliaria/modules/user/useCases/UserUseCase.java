package ucan.reis_imobiliaria.modules.user.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ucan.reis_imobiliaria.exceptions.ResourceNotFoundException;
import ucan.reis_imobiliaria.exceptions.UserFoundException;
import ucan.reis_imobiliaria.modules.user.dto.ChangePasswordDTO;
import ucan.reis_imobiliaria.modules.user.dto.UserDTO;
import ucan.reis_imobiliaria.modules.user.dto.UserUpdateDTO;
import ucan.reis_imobiliaria.modules.user.entities.User;
import ucan.reis_imobiliaria.modules.user.repository.UserRepository;

@Service
public class UserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Transactional
    public User updateUser(UUID id, UserUpdateDTO userUpdateDTO) {
        User user = findById(id);
        
        user.setUsername(userUpdateDTO.getUsername());
        user.setFullName(userUpdateDTO.getFullName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPhone(userUpdateDTO.getPhone());
        user.setNif(userUpdateDTO.getNif());
        user.setAddress(userUpdateDTO.getAddress());
        user.setNationality(userUpdateDTO.getNationality());
        user.setMaritalStatus(userUpdateDTO.getMaritalStatus());

        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(UUID id, ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }
}
