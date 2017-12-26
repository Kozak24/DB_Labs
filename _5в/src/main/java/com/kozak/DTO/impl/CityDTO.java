package com.kozak.DTO.impl;

import com.kozak.DTO.DTO;
import com.kozak.controller.WarehouseController;
import com.kozak.domain.City;
import com.kozak.exceptions.NoSuchCityException;
import com.kozak.exceptions.NoSuchWarehouseException;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


public class CityDTO extends DTO<City> {
    public CityDTO(City city, Link link) throws NoSuchCityException, NoSuchWarehouseException {
        super(city, link);
        add(linkTo(methodOn(WarehouseController.class).getWarehousesByCityID(getEntity().getId())).withRel("warehouses"));
    }

    public Long getCityId() { return getEntity().getId(); }

    public String getCity() {
        return getEntity().getCity();
    }
}