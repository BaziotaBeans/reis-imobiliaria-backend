package ucan.reis_imobiliaria.modules.company;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import ucan.reis_imobiliaria.modules.user.entities.User;

@Data
@Entity(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkCompany;

    private String nif;
    
    private String bankName;
    
    private String bankAccountNumber;

    @Column(columnDefinition = "boolean default true")
    private boolean status;

    // @Length(min = 14, max = 14)
    private String iban;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fkUser")
    private User user;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}   
