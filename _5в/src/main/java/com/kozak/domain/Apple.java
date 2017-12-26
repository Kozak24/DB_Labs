package com.kozak.domain;

import com.kozak.DTO.EntityInterface;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "apple")
public class Apple implements EntityInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apple_id", nullable = false)
    private Long id;

    @Column(name = "apple_name", nullable = false, length = 45)
    private String appleName;

    @Column(name = "inventor", nullable = false, length = 45)
    private String inventor;

    @Column(name = "country", nullable = true, length = 50)
    private String country;

    @Column(name = "year", nullable = true)
    private Integer year;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @ManyToMany(mappedBy = "apples")
    private Set<Warehouse> warehouses;

    Apple(){}

    Apple(String appleName, String inventor, String country, Integer year){
        this.appleName = appleName;
        this.inventor = inventor;
        this.country = country;
        this.year = year;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long idApple) {
        this.id = idApple;
    }

    public String getAppleName() {
        return appleName;
    }
    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }

    public String getInventor() {
        return inventor;
    }
    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }
    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apple that = (Apple) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (appleName != null ? !appleName.equals(that.appleName) : that.appleName != null) return false;
        if (inventor != null ? !inventor.equals(that.inventor) : that.inventor != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (appleName != null ? appleName.hashCode() : 0);
        result = 31 * result + (inventor != null ? inventor.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}