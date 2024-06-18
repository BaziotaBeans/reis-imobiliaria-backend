package ucan.reis_imobiliaria.modules.user.payload.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UUID pkUser;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private List<String> roles;
    private String nif;
    private String address;
    private String nationality;
    private String maritalStatus;
    private Date expirationDate;

    public JwtResponse(
            String accessToken,
            UUID pkUser,
            String username,
            String fullName,
            String email,
            String phone,
            List<String> roles,
            String nif,
            String address,
            String nationality,
            String maritalStatus,
            Date expirationDate) {
        this.token = accessToken;
        this.pkUser = pkUser;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
        this.nif = nif;
        this.address = address;
        this.nationality = nationality;
        this.maritalStatus = maritalStatus;
        this.expirationDate = expirationDate;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public UUID getPkUser() {
        return pkUser;
    }

    public void setId(UUID pkUser) {
        this.pkUser = pkUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
