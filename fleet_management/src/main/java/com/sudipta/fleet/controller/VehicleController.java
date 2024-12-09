package com.sudipta.fleet.controller;

import com.sudipta.fleet.dto.VehicleDto;
import com.sudipta.fleet.exception.RequestDataMissingException;
import com.sudipta.fleet.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class VehicleController {

    private final Logger logger = LoggerFactory.getLogger(VehicleController.class);
    @Autowired
    private VehicleService vehicleService;

    public VehicleController(){}
    VehicleController(VehicleService vehicleService){
        this.vehicleService =vehicleService;
    }

    @PostMapping("/vehicle")
    public ResponseEntity<Object> addVehicle(@RequestBody VehicleDto vehicleDto){
        logger.info("addVehicle method invoked.");
        VehicleDto response=null;
        try{
            if(vehicleDto ==null){
                throw new RequestDataMissingException("Missing data in request.");
            }else{
                response = vehicleService.addVehicle(vehicleDto);
                return ResponseEntity.ok().body(response);
            }
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PutMapping("/vehicle")
    public ResponseEntity<Object> updateVehicle(@RequestBody VehicleDto vehicleDto){
        logger.info("updateVehicle method invoked.");
        VehicleDto response=null;
        try{
            if(vehicleDto ==null){
                throw new RequestDataMissingException("Missing data in request.");
            }else{
                response = vehicleService.updateVehicle(vehicleDto);
                return ResponseEntity.ok().body(response);
            }
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @GetMapping("/vehicle")
    public ResponseEntity<Object> getAllWareHouses(@RequestParam("reg_no") String reg_no){
        try{
            if(reg_no == null){
                throw new RequestDataMissingException("regno no missing in request.");
            }
            VehicleDto warehouseDto = vehicleService.getVehicle(reg_no);
            return ResponseEntity.ok(warehouseDto);
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }

    }
}
