package ucan.reis_imobiliaria.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;
}
