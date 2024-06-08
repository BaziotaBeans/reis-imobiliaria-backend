package ucan.reis_imobiliaria.modules.property.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ucan.reis_imobiliaria.modules.company.CompanyEntity;
import ucan.reis_imobiliaria.modules.property.utils.property.PropertyUtil.PropertyStatus;
import ucan.reis_imobiliaria.modules.propertyImage.entities.PropertyImageEntity;

@Data
@Entity
@Table(name = "property")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkProperty;

    private String title;

    private String province;

    private String county;

    private String address;

    private int suits;

    private int room;

    private int bathroom;

    private int vacancy;

    private double price;

    private double totalArea;

    private double buildingArea;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String paymentModality;

    @Column(columnDefinition = "boolean default true")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkCompany")
    private CompanyEntity companyEntity;

    @Column(name = "fkCompany", nullable = false, insertable = false, updatable = false)
    private UUID fkCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkPropertyType")
    private PropertyTypeEntity fkPropertyTypeEntity;

    @Column(name = "fkPropertyType", nullable = false, insertable = false, updatable = false)
    private UUID fkPropertyType;

    @JsonManagedReference
    @JsonBackReference
    @OneToMany(mappedBy = "propertyImages", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PropertyImageEntity> propertyImages = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus propertyStatus;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PropertyScheduleEntity> schedules;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public PropertyEntity() {

    }

    public PropertyEntity(String title, String province, String county, String address, int suits, int room,
            int bathroom, int vacancy, int price, double totalArea, double buildingArea, String description,
            String paymentModality, boolean status, UUID fkCompany, UUID fkPropertyType) {
        this.title = title;
        this.province = province;
        this.county = county;
        this.address = address;
        this.suits = suits;
        this.room = room;
        this.bathroom = bathroom;
        this.vacancy = vacancy;
        this.price = price;
        this.totalArea = totalArea;
        this.buildingArea = buildingArea;
        this.description = description;
        this.paymentModality = paymentModality;
        this.status = status;
        this.fkCompany = fkCompany;
        this.fkPropertyType = fkPropertyType;
    }
}