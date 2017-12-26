package com.my;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Apple", schema ="jdbc", catalog = "")
public class AppleEntity{
    private int idApple;
    private String appleName;
    private int amount;
    private PriceEntity priceByPrice;
    private List<WarehouseEntity> warehouses;

    public AppleEntity (){}
    public AppleEntity (String a, String idPrice){
        appleName = a;
        priceByPrice = new PriceEntity(idPrice);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Apple", nullable = false)
    public int getIdApple() {
        return idApple;
    }

    public void setIdApple(int idApple) {
        this.idApple = idApple;
    }

    @Basic
    @Column(name = "AppleName", nullable = false, length = 25)
    public String getAppleName() {
        return appleName;
    }

    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }

    @Basic
    @Column(name = "Amount", nullable = false)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppleEntity that = (AppleEntity) o;

        if (idApple != that.idApple) return false;
        if (amount != that.amount) return false;
        if (appleName != null ? !appleName.equals(that.appleName) : that.appleName != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = idApple;
        result = 31 * result + (appleName != null ? appleName.hashCode() : 0);
        result = 31 * result + amount;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Price", referencedColumnName = "ID_Price", nullable = false)
    public PriceEntity getPriceByPrice() {
        return priceByPrice;
    }

    public void setPriceByPrice(PriceEntity priceByPrice) {
        this.priceByPrice = priceByPrice;
    }

    @ManyToMany
    @JoinTable(name = "AppleWarehouse", catalog = "", schema = "jdbc", joinColumns = @JoinColumn(name = "ID_Apple", referencedColumnName = "ID_Apple", nullable = false), inverseJoinColumns = @JoinColumn(name = "ID_Warehouse", referencedColumnName = "ID_Warehouse", nullable = false))
    public List<WarehouseEntity> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseEntity> warehouses) {
        this.warehouses = warehouses;
    }
}
