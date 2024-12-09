package com.sudipta.fleet.controller;

import com.sudipta.fleet.dto.WarehouseDto;
import com.sudipta.fleet.dto.WarehouseVehicleAssigmentRequest;
import com.sudipta.fleet.dto.WarehouseVehicleResponse;
import com.sudipta.fleet.exception.RequestDataMissingException;
import com.sudipta.fleet.service.WarehouseVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class WarehouseVehicleController {

    @Autowired
    private WarehouseVehicleService warehouseVehicleService;

    private final Logger logger = LoggerFactory.getLogger(WarehouseVehicleController.class);
    @PostMapping("/vehicle-assigment")
    public ResponseEntity<Object> addWarehouse(@RequestBody WarehouseVehicleAssigmentRequest vehicleAssigmentRequest){
        logger.info("addWarehouse method invoked.");
        WarehouseDto response=null;
        try{
            if(vehicleAssigmentRequest ==null || vehicleAssigmentRequest.getRegno()==null ||
                    vehicleAssigmentRequest.getWarehoseId()==0 || vehicleAssigmentRequest.getStartDate()==null){
                throw new RequestDataMissingException("Missing data in request.");
            }else{
                warehouseVehicleService.assignVehicleToWarehouse(vehicleAssigmentRequest);
                return ResponseEntity.ok().body("response");
            }
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }

    }

    @GetMapping("/vehicle-assigment")
    public List<WarehouseVehicleResponse> getAssigmentDetails(@RequestParam("regNo") String regNo){
        return warehouseVehicleService.getVehicleInWarehouse(regNo);
    }

    @GetMapping("/vehicle-details")
    public List<WarehouseVehicleResponse> getVehicleDetails(@RequestParam("warehouseId")String warehouseId){
        return warehouseVehicleService.getVehicleDetails(Integer.parseInt(warehouseId));
    }

    @GetMapping("/available-vehicle")
    public List<WarehouseVehicleResponse> getAvailableVehicleDetails(@RequestParam("warehouseId")String warehouseId, @RequestParam("fromDate")String fromDate,
                                                                     @RequestParam("toDate") String toDate) throws ParseException {
        return warehouseVehicleService.getAvailableVehicleDetails(Integer.parseInt(warehouseId), fromDate, toDate);
    }
}
