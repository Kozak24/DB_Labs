package com.kozak.controller;

import com.kozak.DTO.DTOBuilder;
import com.kozak.DTO.impl.WarehouseDTO;
import com.kozak.domain.Warehouse;
import com.kozak.exceptions.*;
import com.kozak.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @GetMapping(value = "/api/warehouse/city/{city_id}")
    public ResponseEntity<List<WarehouseDTO>> getWarehousesByCityID(@PathVariable Long city_id) throws NoSuchCityException, NoSuchWarehouseException {
        List<Warehouse> warehouseList = warehouseService.getWarehouseByCityId(city_id);

        Link link = linkTo(methodOn(WarehouseController.class).getAllWarehouses()).withSelfRel();

        List<WarehouseDTO> warehouseDTO = DTOBuilder.buildDtoListForCollection(warehouseList, WarehouseDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/warehouse/{warehouse_id}")
    public ResponseEntity<WarehouseDTO> getWarehouse(@PathVariable Long warehouse_id) throws NoSuchWarehouseException {
        Warehouse warehouse = warehouseService.getWarehouse(warehouse_id);
        Link link = linkTo(methodOn(WarehouseController.class).getWarehouse(warehouse_id)).withSelfRel();
        WarehouseDTO warehouseDTO = DTOBuilder.buildDtoForEntity(warehouse, WarehouseDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/warehouse")
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouses() {
        List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
        Link link = linkTo(methodOn(WarehouseController.class).getAllWarehouses()).withSelfRel();
        List<WarehouseDTO> cities = DTOBuilder.buildDtoListForCollection(warehouseList, WarehouseDTO.class, link);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping(value = "/api/warehouse/apple/{apple_id}")
    public ResponseEntity<List<WarehouseDTO>> getWarehousesByAppleID(@PathVariable Long apple_id) throws NoSuchAppleException {
        Set<Warehouse> warehouseList = warehouseService.getWarehousesByAppleId(apple_id);
        Link link = linkTo(methodOn(WarehouseController.class).getAllWarehouses()).withSelfRel();
        List<WarehouseDTO> warehouseDTO = DTOBuilder.buildDtoListForCollection(warehouseList, WarehouseDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/warehouse/city/{city_id}")
    public  ResponseEntity<WarehouseDTO> addWarehouse(@RequestBody Warehouse newWarehouse, @PathVariable Long city_id)
            throws NoSuchCityException, NoSuchWarehouseException {
        warehouseService.createWarehouse(newWarehouse, city_id);
        Link link = linkTo(methodOn(WarehouseController.class).getWarehouse(newWarehouse.getId())).withSelfRel();
        WarehouseDTO warehouseDTO = DTOBuilder.buildDtoForEntity(newWarehouse,WarehouseDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/warehouse/{warehouse_id}/city/{city_id}")
    public  ResponseEntity<WarehouseDTO> updateWarehouse(@RequestBody Warehouse uWarehouse,
                                                         @PathVariable Long warehouse_id, @PathVariable Long city_id)
            throws NoSuchCityException, NoSuchWarehouseException {
        warehouseService.updateWarehouse(uWarehouse, warehouse_id, city_id);
        Warehouse warehouse = warehouseService.getWarehouse(warehouse_id);
        Link link = linkTo(methodOn(WarehouseController.class).getWarehouse(warehouse_id)).withSelfRel();
        WarehouseDTO warehouseDTO = DTOBuilder.buildDtoForEntity(warehouse, WarehouseDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/warehouse/{warehouse_id}")
    public  ResponseEntity deleteWarehouse(@PathVariable Long warehouse_id) throws NoSuchWarehouseException, ExistsApplesForWarehouseException {
        warehouseService.deleteWarehouse(warehouse_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/api/warehouse/{warehouse_id}/apple/{apple_id}")
    public  ResponseEntity<WarehouseDTO> addAppleForWarehouse(@PathVariable Long warehouse_id, @PathVariable Long apple_id)
            throws NoSuchWarehouseException, NoSuchAppleException, AlreadyExistsAppleInWarehouseException, AppleAbsentException {
        warehouseService.addAppleForWarehouse(warehouse_id,apple_id);
        Warehouse warehouse = warehouseService.getWarehouse(warehouse_id);
        Link link = linkTo(methodOn(WarehouseController.class).getWarehouse(warehouse_id)).withSelfRel();
        WarehouseDTO warehouseDTO = DTOBuilder.buildDtoForEntity(warehouse, WarehouseDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/warehouse/{warehouse_id}/apple/{apple_id}")
    public  ResponseEntity<WarehouseDTO> removeAppleForWarehouse(@PathVariable Long warehouse_id, @PathVariable Long apple_id)
            throws NoSuchWarehouseException, NoSuchAppleException, WarehouseHasNotAppleException {
        warehouseService.removeAppleForWarehouse(warehouse_id, apple_id);
        Warehouse warehouse = warehouseService.getWarehouse(warehouse_id);
        Link link = linkTo(methodOn(WarehouseController.class).getWarehouse(warehouse_id)).withSelfRel();
        WarehouseDTO warehouseDTO = DTOBuilder.buildDtoForEntity(warehouse, WarehouseDTO.class, link);
        return new ResponseEntity<>(warehouseDTO, HttpStatus.OK);
    }

}
