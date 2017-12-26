package com.my;


import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Price", schema = "jdbc", catalog = "")
public class PriceEntity {
    private int idPrice;
    private String price;
    private Collection<AppleEntity> appleByPrice;
    public PriceEntity (){}
    public PriceEntity(String p){
        price = p;
    }

    @Id
    @Column(name = "ID_Price", nullable = false, length = 25)
    public int getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(int idPrice) {
        this.idPrice = idPrice;
    }

    @Basic
    @Column(name = "Price", nullable = false, length = 25)
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceEntity that = (PriceEntity) o;
        if (idPrice != that.idPrice) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPrice;
        result = price != null ? price.hashCode() : 0;
        return result;
    }

    @OneToMany(mappedBy = "priceByPrice")
    public Collection<AppleEntity> getAppleByPrice() {
        return appleByPrice;
    }

    public void setAppleByPrice(Collection<AppleEntity> appleByPrice) {
        this.appleByPrice = appleByPrice;
    }
}
