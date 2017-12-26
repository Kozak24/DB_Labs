package com.kozak.DTO.impl;

import com.kozak.DTO.DTO;
import com.kozak.controller.WarehouseController;
import com.kozak.domain.Apple;
import com.kozak.exceptions.NoSuchAppleException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AppleDTO extends DTO<Apple> {
    public AppleDTO(Apple apple, Link link) throws NoSuchAppleException {
        super(apple, link);
        add(linkTo(methodOn(WarehouseController.class).getWarehousesByAppleID(getEntity().getId())).withRel("warehouses"));
    }

    public Long getAppleId() {
        return getEntity().getId();
    }

    public String getAppleName() {
        return getEntity().getAppleName();
    }

    public String getInventor() {
        return getEntity().getInventor();
    }

    public String getCountry() {
        return getEntity().getCountry();
    }

    public Integer getYear() {
        return getEntity().getYear();
    }

    public Integer getAmount() {
        return getEntity().getAmount();
    }

}
