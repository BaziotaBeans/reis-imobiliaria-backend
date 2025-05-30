package ucan.reis_imobiliaria.modules.user.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkUser;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private String phone;

    // @NotBlank
    @Size(max = 120)
    private String password;

    private String nif;

    private String address;

    private String nationality;

    private String maritalStatus;

    private String urlDocument;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles", 
          joinColumns = @JoinColumn(name = "user_id"), 
          inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }
  
    public User(String username, String email, String password, String phone) {
      this.username = username;
      this.email = email;
      this.phone = phone;
      this.password = password;
    }
   
    public User(String username, String fullName, String email, String password, String phone) {
      this.username = username;
      this.email = email;
      this.fullName = fullName;
      this.phone = phone;
      this.password = password;
    }
    
    public User(
      String username, 
      String fullName, 
      String email, 
      String password, 
      String phone, 
      String address, 
      String nif,
      String nationality,
      String maritalStatus,
      String urlDocument
    ) {
      this.username = username;
      this.email = email;
      this.fullName = fullName;
      this.phone = phone;
      this.password = password;
      this.address = address;
      this.nif = nif;
      this.nationality = nationality;
      this.maritalStatus = maritalStatus;
      this.urlDocument = urlDocument;
    }
}
