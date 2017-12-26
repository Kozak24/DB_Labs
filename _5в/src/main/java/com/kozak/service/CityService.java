package com.kozak.service;

import com.kozak.Repository.CityRepository;
import com.kozak.Repository.WarehouseRepository;
import com.kozak.domain.City;
import com.kozak.domain.Warehouse;
import com.kozak.exceptions.ExistsWarehousesForCityException;
import com.kozak.exceptions.NoSuchCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;
    private boolean ascending;

    @Autowired
    WarehouseRepository warehouseRepository;

    public List<City> getAllCity() {
        return cityRepository.findAll();
    }

    public City getCity(Long city_id) throws NoSuchCityException {
//        City city = cityRepository.findOne(city_id);//1.5.9
        City city = cityRepository.findById(city_id).get();//2.0.0.M7
        if (city == null) throw new NoSuchCityException();
        return city;
    }

    @Transactional
    public void createCity(City city) {
        cityRepository.save(city);
    }

    @Transactional
    public void updateCity(City uCity, Long city_id) throws NoSuchCityException {
//        City city = cityRepository.findOne(city_id);//1.5.9
        City city = cityRepository.findById(city_id).get();//2.0.0.M7

        if (city == null) throw new NoSuchCityException();
        city.setCity(uCity.getCity());
        cityRepository.save(city);
    }

    @Transactional
    public void deleteCity(Long city_id) throws NoSuchCityException, ExistsWarehousesForCityException {
//        City city = cityRepository.findOne(city_id);//1.5.9
        City city = cityRepository.findById(city_id).get();//2.0.0.M7
        if (city == null) throw new NoSuchCityException();
        if (city.getWarehouses().size() != 0) throw new ExistsWarehousesForCityException();
        cityRepository.delete(city);
    }

}
