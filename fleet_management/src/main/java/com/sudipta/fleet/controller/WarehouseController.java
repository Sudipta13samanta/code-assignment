package com.sudipta.fleet.controller;

import com.sudipta.fleet.dto.WarehouseDto;
import com.sudipta.fleet.exception.RequestDataMissingException;
import com.sudipta.fleet.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {

    private final Logger logger = LoggerFactory.getLogger(WarehouseController.class);
    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/warehouse")
    public ResponseEntity<Object> addWarehouse(@RequestBody WarehouseDto warehouseDto){
        logger.info("addWarehouse method invoked.");
        WarehouseDto response=null;
        try{
            if(warehouseDto ==null){
                throw new RequestDataMissingException("Missing data in request.");
            }else{
                response = warehouseService.addWarehouse(warehouseDto);
                return ResponseEntity.ok().body(response);
            }
        }catch(RequestDataMissingException ex){
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }

    }

    @PutMapping("/warehouse")
    public ResponseEntity<Object> updateWarehouse(@RequestBody WarehouseDto warehouseDto){
        logger.info("updateWarehouse method invoked.");
        WarehouseDto response=null;
        try{
            if(warehouseDto ==null){
                throw new RequestDataMissingException("Missing data in request.");
            }else{
                response = warehouseService.updateWarehouse(warehouseDto);
                return ResponseEntity.ok().body(response);
            }
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @GetMapping("/warehousees")
    public ResponseEntity<Object> getAllWareHouses(@RequestParam("pin") String pin){
        try{
            if(pin == null){
                throw new RequestDataMissingException("Pin no missing in request.");
            }
            List<WarehouseDto> warehouseDtoList = warehouseService.getWarehouse(pin);
            return ResponseEntity.ok(warehouseDtoList);
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }

    }
}
