package ucan.reis_imobiliaria.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String phone;

    private String nif;

    private String address;

    private String nationality;

    private String maritalStatus;
}
