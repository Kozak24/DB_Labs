package com.kozak.controller;

import com.kozak.DTO.DTOBuilder;
import com.kozak.DTO.impl.AppleDTO;

import com.kozak.domain.Apple;
import com.kozak.exceptions.*;
import com.kozak.service.AppleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class AppleController {
    @Autowired
    AppleService appleService;

    @GetMapping(value = "/api/apple/warehouse/{warehouse_id}")
    public ResponseEntity<List<AppleDTO>> getApplesByWarehouseID(@PathVariable Long warehouse_id) throws NoSuchWarehouseException {
        Set<Apple> appleList = appleService.getApplesByWarehouseId(warehouse_id);
        Link link = linkTo(methodOn(AppleController.class).getAllApples()).withSelfRel();
        List<AppleDTO> warehouseDTO = DTOBuilder.buildDtoListForCollection(appleList, AppleDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/apple/{apple_id}")
    public ResponseEntity<AppleDTO> getApple(@PathVariable Long apple_id) throws NoSuchAppleException {
        Apple apple = appleService.getApple(apple_id);
        Link link = linkTo(methodOn(AppleController.class).getApple(apple_id)).withSelfRel();
        AppleDTO appleDTO = DTOBuilder.buildDtoForEntity(apple, AppleDTO.class, link);
        return new ResponseEntity<>(appleDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/apple")
    public ResponseEntity<List<AppleDTO>> getAllApples()  {
        List<Apple> appleList = appleService.getAllApples();
        Link link = linkTo(methodOn(AppleController.class).getAllApples()).withSelfRel();
        List<AppleDTO> warehouseDTO = DTOBuilder.buildDtoListForCollection(appleList, AppleDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/apple")
    public  ResponseEntity<AppleDTO> addApple(@RequestBody Apple newApple) throws NoSuchAppleException {
        appleService.createApple(newApple);
        Link link = linkTo(methodOn(AppleController.class).getApple(newApple.getId())).withSelfRel();
        AppleDTO appleDTO = DTOBuilder.buildDtoForEntity(newApple, AppleDTO.class, link);
        return new ResponseEntity<>(appleDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/apple/{apple_id}")
    public  ResponseEntity<AppleDTO> updateApple(@RequestBody Apple uApple, @PathVariable Long apple_id) throws NoSuchAppleException {
        appleService.updateApple(uApple,apple_id);
        Apple apple = appleService.getApple(apple_id);
        Link link = linkTo(methodOn(AppleController.class).getApple(apple_id)).withSelfRel();
        AppleDTO appleDTO = DTOBuilder.buildDtoForEntity(apple,AppleDTO.class, link);
        return new ResponseEntity<>(appleDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/apple/{apple_id}")
    public  ResponseEntity deleteApple(@PathVariable Long apple_id) throws ExistsWarehouseForAppleException, NoSuchAppleException {
        appleService.deleteApple(apple_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
