package com.kozak.service;

import com.kozak.Repository.*;
import com.kozak.domain.*;
import com.kozak.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AppleRepository appleRepository;

    public List<Warehouse> getWarehouseByCityId(Long city_id) throws NoSuchCityException {
//        City city = cityRepository.findOne(city_id);//1.5.9
        City city = cityRepository.findById(city_id).get();//2.0.0.M7
        if (city == null) throw new NoSuchCityException();
        return city.getWarehouses();
    }

    public Warehouse getWarehouse(Long warehouse_id) throws NoSuchWarehouseException {
//        Warehouse warehouse = warehouse.findOne(warehouse_id);//1.5.9
        Warehouse warehouse = warehouseRepository.findById(warehouse_id).get();//2.0.0.M7
        if (warehouse == null) throw new NoSuchWarehouseException();
        return warehouse;
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Set<Warehouse> getWarehousesByAppleId(Long apple_id) throws NoSuchAppleException {
//        Apple apple = appleRepository.findOne(apple_id);//1.5.9
        Apple apple = appleRepository.findById(apple_id).get();//2.0.0.M7
        if (apple == null) throw new NoSuchAppleException();
        return apple.getWarehouses();
    }

    @Transactional
    public void createWarehouse(Warehouse warehouse, Long city_id) throws NoSuchCityException {
        if (city_id > 0) {
//            City city = cityRepository.findOne(city_id);//1.5.9
            City city = cityRepository.findById(city_id).get();//2.0.0.M7

            if (city == null) throw new NoSuchCityException();
            warehouse.setCity(city);
        }
        warehouseRepository.save(warehouse);
    }

    @Transactional
    public void updateWarehouse(Warehouse uWarehouse, Long warehouse_id, Long city_id) throws NoSuchCityException, NoSuchWarehouseException {
//        City city = cityRepository.findOne(city_id);//1.5.9
        City city = cityRepository.findById(city_id).get();//2.0.0.M7

        if (city_id > 0) {
            if (city == null) throw new NoSuchCityException();
        }
//        Warehouse warehouse = warehouseRepository.findOne(warehouse_id);//1.5.9
        Warehouse warehouse = warehouseRepository.findById(warehouse_id).get();//2.0.0.M7
        if (warehouse == null) throw new NoSuchWarehouseException();
        //update
        warehouse.setWarehousename(uWarehouse.getWarehousename());
        warehouse.setLogo(uWarehouse.getLogo());
        warehouse.setEmail(uWarehouse.getEmail());
        if (city_id > 0) warehouse.setCity(city);
        else warehouse.setCity(null);
        warehouse.setStreet(uWarehouse.getStreet());
        warehouse.setApartment(uWarehouse.getApartment());
        warehouseRepository.save(warehouse);
    }

    @Transactional
    public void deleteWarehouse(Long warehouse_id) throws NoSuchWarehouseException, ExistsApplesForWarehouseException {
//        Warehouse warehouse = warehouseRepository.findOne(warehouse_id);//1.5.9
        Warehouse warehouse = warehouseRepository.findById(warehouse_id).get();//2.0.0.M7
        if (warehouse == null) throw new NoSuchWarehouseException();
        if (warehouse.getApples().size() != 0) throw new ExistsApplesForWarehouseException();
        warehouseRepository.delete(warehouse);
    }

    @Transactional
    public void addAppleForWarehouse(Long warehouse_id, Long apple_id)
            throws NoSuchWarehouseException, NoSuchAppleException, AlreadyExistsAppleInWarehouseException, AppleAbsentException {
//        Warehouse warehouse = warehouseRepository.findOne(warehouse_id);//1.5.9
        Warehouse warehouse = warehouseRepository.findById(warehouse_id).get();//2.0.0.M7
        if (warehouse == null) throw new NoSuchWarehouseException();
//        Apple apple = appleRepository.findOne(apple_id);//1.5.9
        Apple apple = appleRepository.findById(apple_id).get();//2.0.0.M7
        if (apple == null) throw new NoSuchAppleException();
        if (warehouse.getApples().contains(apple) == true) throw new AlreadyExistsAppleInWarehouseException();
        if (apple.getAmount() <= apple.getWarehouses().size()) throw new AppleAbsentException();
        warehouse.getApples().add(apple);
        warehouseRepository.save(warehouse);
    }

    @Transactional
    public void removeAppleForWarehouse(Long warehouse_id, Long apple_id)
            throws NoSuchWarehouseException, NoSuchAppleException, WarehouseHasNotAppleException {
//        Warehouse warehouse = warehouseRepository.findOne(warehouse_id);//1.5.9
        Warehouse warehouse = warehouseRepository.findById(warehouse_id).get();//2.0.0.M7
        if (warehouse == null) throw new NoSuchWarehouseException();
//        Apple apple = appleRepository.findOne(apple_id);//1.5.9
        Apple apple = appleRepository.findById(apple_id).get();//2.0.0.M7
        if (apple == null) throw new NoSuchAppleException();
        if (warehouse.getApples().contains(apple) == false) throw new WarehouseHasNotAppleException();
        warehouse.getApples().remove(apple);
        warehouseRepository.save(warehouse);
    }


}
