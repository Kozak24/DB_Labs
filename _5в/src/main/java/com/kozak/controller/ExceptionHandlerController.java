package com.kozak.controller;

import com.kozak.DTO.impl.MessageDTO;
import com.kozak.exceptions.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchCityException.class)
    ResponseEntity<MessageDTO> handleNoSushCityException(){
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such city not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchWarehouseException.class)
    ResponseEntity<MessageDTO> handleNoSuchWarehouseException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such warehouse not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchAppleException.class)
    ResponseEntity<MessageDTO> handleNoSushAppleException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such apple not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsWarehousesForCityException.class)
    ResponseEntity<MessageDTO> handleExistsWarehousesForCityException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete imposible. There are warehouses for this city"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistsApplesForWarehouseException.class)
    ResponseEntity<MessageDTO> handleExistsApplesForWarehouseException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete imposible. There are apples for this warehouse"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistsWarehouseForAppleException.class)
    ResponseEntity<MessageDTO> handleExistsWarehouseForAppleException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete imposible. There are warehouses for this apple"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyExistsAppleInWarehouseException.class)
    ResponseEntity<MessageDTO> handleAlreadyExistsAppleInWarehouseException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Add imposible. The warehouse already contain this apple"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AppleAbsentException.class)
    ResponseEntity<MessageDTO> handleAppleAbsentException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Now this apple is absent"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WarehouseHasNotAppleException.class)
    ResponseEntity<MessageDTO> handleWarehouseHasNotAppleException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("The warehouse hasn't this apple"), HttpStatus.NOT_FOUND);
    }

}
