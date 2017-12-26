package com.kozak.DTO.impl;

import com.kozak.DTO.DTO;
import com.kozak.controller.AppleController;
import com.kozak.domain.Warehouse;
import com.kozak.exceptions.NoSuchWarehouseException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class WarehouseDTO extends DTO<Warehouse> {
    public WarehouseDTO(Warehouse warehouse, Link link) throws NoSuchWarehouseException {
        super(warehouse, link);
        add(linkTo(methodOn(AppleController.class).getApplesByWarehouseID(getEntity().getId())).withRel("apples"));
    }

    public Long getWarehouseId() {
        return getEntity().getId();
    }

    public String getWarehousename() {
        return getEntity().getWarehousename();
    }

    public String getLogo() {
        return getEntity().getLogo();
    }

    public String getEmail() {
        return getEntity().getEmail();
    }

    public String getCity() {
        if(getEntity().getCity()==null) return "";
        return getEntity().getCity().getCity();
    }

    public String getStreet() {
        return getEntity().getStreet();
    }

    public String getApartment() {
        return getEntity().getApartment();
    }


}
