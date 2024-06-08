package ucan.reis_imobiliaria.modules.user.payload.response;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class SignUpResponse {
    private UUID pkUser;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String message;
    private String nif;
    private String address;
    private String nationality;
    private String maritalStatus;

    public SignUpResponse(
        UUID pkUser, 
        String username, 
        String fullName, 
        String email, 
        String phone, 
        String nif,
        String address,
        String nationality,
        String maritalStatus,
        String message
    ) {
        this.pkUser = pkUser;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.message = message;
        this.nif = nif;
        this.address = address;
        this.nationality = nationality;
        
    }
}
