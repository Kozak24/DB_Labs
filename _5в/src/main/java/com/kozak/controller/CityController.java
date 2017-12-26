package com.kozak.controller;

import com.kozak.DTO.DTOBuilder;
import com.kozak.DTO.impl.CityDTO;
import com.kozak.domain.City;
import com.kozak.exceptions.ExistsWarehousesForCityException;
import com.kozak.exceptions.NoSuchCityException;
import com.kozak.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class CityController {
    @Autowired
    CityService cityService;

    @GetMapping(value = "/api/city")
    public ResponseEntity<List<CityDTO>> getAllCities() {
        List<City> cityList = cityService.getAllCity();
        Link link = linkTo(methodOn(CityController.class).getAllCities()).withSelfRel();
        List<CityDTO> cities = DTOBuilder.buildDtoListForCollection(cityList, CityDTO.class, link);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping(value = "/api/city/{city_id}")
    public ResponseEntity<CityDTO> getCity(@PathVariable Long city_id) throws NoSuchCityException {
        City city = cityService.getCity(city_id);
        Link link = linkTo(methodOn(CityController.class).getCity(city_id)).withSelfRel();
        CityDTO cityDTO = DTOBuilder.buildDtoForEntity(city,CityDTO.class, link);
        return new ResponseEntity<>(cityDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/city/{city_id}")
    public  ResponseEntity<CityDTO> addCity(@RequestBody City newCity) throws NoSuchCityException {
        cityService.createCity(newCity);
        Link link = linkTo(methodOn(CityController.class).getCity(newCity.getId())).withSelfRel();
        CityDTO cityDTO = DTOBuilder.buildDtoForEntity(newCity,CityDTO.class, link);
        return new ResponseEntity<>(cityDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/city/{city_id}")
    public  ResponseEntity<CityDTO> updateCity(@RequestBody City ucity, @PathVariable Long city_id) throws NoSuchCityException {
        cityService.updateCity(ucity, city_id);
        City city = cityService.getCity(city_id);
        Link link = linkTo(methodOn(CityController.class).getCity(city_id)).withSelfRel();
        CityDTO cityDTO = DTOBuilder.buildDtoForEntity(city,CityDTO.class, link);
        return new ResponseEntity<>(cityDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/city/{city_id}")
    public  ResponseEntity deleteCity(@PathVariable Long city_id) throws NoSuchCityException, ExistsWarehousesForCityException {
        cityService.deleteCity(city_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
