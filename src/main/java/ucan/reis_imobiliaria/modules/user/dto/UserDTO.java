package ucan.reis_imobiliaria.modules.user.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    private String phone;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String nif;

    private String address;

    private String nationality;

    private String maritalStatus; 
}
