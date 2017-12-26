package com.my;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "warehouse", schema = "jdbc", catalog = "")
public class WarehouseEntity {
    private int idWarehouse;
    private String warehouseName;
    private String city;
    private String street;
    private String email;
    private List<AppleEntity> apples;

    public WarehouseEntity(){}
    public WarehouseEntity(String w,String c,String s,String e){
        warehouseName = w;
        city = c;
        street = s;
        email = e;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Warehouse", nullable = false)
    public int getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(int idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    @Column(name = "WarehouseName", nullable = false, length = 25)
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Column(name = "City", nullable = false, length = 25)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "Street", nullable = false, length = 25)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "Email", nullable = true, length = 25)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarehouseEntity that = (WarehouseEntity) o;

        if (idWarehouse != that.idWarehouse) return false;
        if (warehouseName != null ? !warehouseName.equals(that.warehouseName) : that.warehouseName != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idWarehouse;
        result = 31 * result + (warehouseName != null ? warehouseName.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "warehouses")
    public List<AppleEntity> getApples() {
        return apples;
    }

    public void addAppleEntity(AppleEntity appleEntity){
        if(!getApples().contains(appleEntity)){
            getApples().add(appleEntity);
        }
        if(!appleEntity.getWarehouses().contains(this)){
            appleEntity.getWarehouses().add(this);
        }
    }
    public void setApples(List<AppleEntity> apples) {
        this.apples = apples;
    }
}
