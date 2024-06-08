package ucan.reis_imobiliaria.modules.user.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyRequest {
    @NotBlank
    private String nif;

    @NotBlank
    private String bankName;

    @NotBlank
    private String bankAccountNumber;
    private boolean status;

    @NotBlank
    private String iban;
}
