package com.kozak.service;

import com.kozak.Repository.AppleRepository;
import com.kozak.Repository.WarehouseRepository;
import com.kozak.domain.Apple;
import com.kozak.domain.Warehouse;
import com.kozak.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class AppleService {
    @Autowired
    AppleRepository appleRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    public Set<Apple> getApplesByWarehouseId(Long warehouse_id) throws NoSuchWarehouseException {
//        Warehouse warehouse = warehouseRepository.findOne(warehouse_id);//1.5.9
        Warehouse warehouse = warehouseRepository.findById(warehouse_id).get();//2.0.0.M7
        if (warehouse == null) throw new NoSuchWarehouseException();
        return warehouse.getApples();
    }

    public Apple getApple(Long apple_id) throws NoSuchAppleException {
//        Apple apple = appleRepository.findOne(apple_id);//1.5.9
        Apple apple = appleRepository.findById(apple_id).get();//2.0.0.M7
        if (apple == null) throw new NoSuchAppleException();
        return apple;
    }

    public List<Apple> getAllApples() {
        return appleRepository.findAll();
    }

    @Transactional
    public void createApple(Apple apple) {
        appleRepository.save(apple);
    }

    @Transactional
    public void updateApple(Apple uApple, Long apple_id) throws NoSuchAppleException {
//        Apple apple = appleRepository.findOne(apple_id);//1.5.9
        Apple apple = appleRepository.findById(apple_id).get();//2.0.0.M7
        if (apple == null) throw new NoSuchAppleException();
        //update
        apple.setAppleName(uApple.getAppleName());
        apple.setInventor(uApple.getInventor());
        apple.setCountry(uApple.getCountry());
        apple.setYear(uApple.getYear());
        apple.setAmount(uApple.getAmount());
    }

    @Transactional
    public void deleteApple(Long apple_id) throws NoSuchAppleException, ExistsWarehouseForAppleException {
//        Apple apple = appleRepository.findOne(apple_id);//1.5.9
        Apple apple = appleRepository.findById(apple_id).get();//2.0.0.M7

        if (apple == null) throw new NoSuchAppleException();
        if (apple.getWarehouses().size() != 0) throw new ExistsWarehouseForAppleException();
        appleRepository.delete(apple);
    }

}
