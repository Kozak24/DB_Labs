package com.kozak.domain;

import com.kozak.DTO.EntityInterface;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "warehouse")
public class Warehouse implements EntityInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id", nullable = false)
    private Long id;

    @Column(name = "warehousename", nullable = false, length = 25)
    private String warehousename;

    @Column(name = "logo", nullable = false, length = 45)
    private String logo;

    @Column(name = "email", nullable = true, length = 45)
    private String email;

    @Column(name = "street", nullable = true, length = 30)
    private String street;

    @Column(name = "apartment", nullable = true, length = 10)
    private String apartment;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private City city;

    @ManyToMany
    @JoinTable(name = "warehouse_apple",
            joinColumns = @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "apple_id", referencedColumnName = "apple_id", nullable = false))
    private Set<Apple> apples;

    Warehouse(){}

    Warehouse(String warehousename, String logo, String email, String street, String apartment){
        this.warehousename = warehousename;
        this.logo = logo;
        this.email = email;
        this.street = street;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long idWarehouse) {
        this.id = idWarehouse;
    }

    public String getWarehousename() {
        return warehousename;
    }
    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String name) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Apple> getApples() {
        return apples;
    }
    public void setApples(Set<Apple> apples) {
        this.apples = apples;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse that = (Warehouse) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (warehousename != null ? !warehousename.equals(that.warehousename) : that.warehousename != null) return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (apartment != null ? !apartment.equals(that.apartment) : that.apartment != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (warehousename != null ? warehousename.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        return result;
    }
}
