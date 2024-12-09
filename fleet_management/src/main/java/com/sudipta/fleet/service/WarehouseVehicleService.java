package com.sudipta.fleet.service;

import com.sudipta.fleet.dto.WarehouseVehicleAssigmentRequest;
import com.sudipta.fleet.dto.WarehouseVehicleResponse;
import com.sudipta.fleet.entity.VehicleEntity;
import com.sudipta.fleet.entity.WarehouseEntity;
import com.sudipta.fleet.entity.WarehouseVehicleEntity;
import com.sudipta.fleet.exception.DataNotFoundException;
import com.sudipta.fleet.exception.RequestDataMissingException;
import com.sudipta.fleet.repo.VehicleRepo;
import com.sudipta.fleet.repo.WarehouseRepo;
import com.sudipta.fleet.repo.WarehouseVehicleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
    public class WarehouseVehicleService {

    private final Logger logger = LoggerFactory.getLogger(WarehouseVehicleService.class);
    private static int uniqueId;
    @Autowired
    private WarehouseVehicleRepo warehouseVehicleRepo;
    @Autowired
    private VehicleRepo vehicleRepo;
    @Autowired
    private WarehouseRepo warehouseRepo;
    @Autowired
    private UtilityService utilityService;

    public void assignVehicleToWarehouse(WarehouseVehicleAssigmentRequest vehicleAssigmentRequest) throws ParseException {
        // find vehicle
        VehicleEntity vehicleEntity = getVehicleEntity(vehicleAssigmentRequest);
        // find warehouse
        WarehouseEntity warehouseEntity = getWarehouseEntity(vehicleAssigmentRequest);

        //check is the vehicle is already assigned
        WarehouseVehicleEntity alreadyAssigneVehicle = warehouseVehicleRepo.findByVehicleId(vehicleEntity.getId());
        if(alreadyAssigneVehicle != null && alreadyAssigneVehicle.getWarehouseId()==warehouseEntity.getId()){
            alreadyAssigneVehicle.setEndDate(new Date());
            warehouseVehicleRepo.save(alreadyAssigneVehicle);
        }
        WarehouseVehicleEntity warehouseVehicleEntity = new WarehouseVehicleEntity();
        warehouseVehicleEntity.setId(uniqueId++);
        warehouseVehicleEntity.setVehicleId(vehicleEntity.getId());
        warehouseVehicleEntity.setWarehouseId(warehouseEntity.getId());
        if(vehicleAssigmentRequest.getStartDate() == null){
            throw new RequestDataMissingException("Start date is missing.");
        }
        warehouseVehicleEntity.setStartDate(utilityService.convertDate(vehicleAssigmentRequest.getStartDate()));
        logger.info("StartDate"+ utilityService.convertDate(vehicleAssigmentRequest.getStartDate()));
        warehouseVehicleEntity.setEndDate(null);
        warehouseVehicleRepo.save(warehouseVehicleEntity);
    }

    private WarehouseEntity getWarehouseEntity(WarehouseVehicleAssigmentRequest vehicleAssigmentRequest) {
        WarehouseEntity warehouseEntity = warehouseRepo.findById(vehicleAssigmentRequest.getWarehoseId());
        if(warehouseEntity == null){
            throw new DataNotFoundException("Warehouse data is not present.");
        }
        return warehouseEntity;
    }

    private VehicleEntity getVehicleEntity(WarehouseVehicleAssigmentRequest vehicleAssigmentRequest) {
        VehicleEntity vehicleEntity = vehicleRepo.findByRegNo(vehicleAssigmentRequest.getRegno());
        if(vehicleEntity == null){
            throw new DataNotFoundException("Vehicle data is not present.");
        }
        return vehicleEntity;
    }

    public List<WarehouseVehicleResponse> getVehicleInWarehouse(String regNo){
        List<Object[]> data = warehouseVehicleRepo.findByVehicleRegNo(regNo);
        if(data.isEmpty()){
            throw new DataNotFoundException("Data is not present.");
        }
        List<WarehouseVehicleResponse> response = new ArrayList<>();
        for(Object object[]: data){
            WarehouseVehicleResponse warehouseVehicleResponse = setWarehouseVehicleResponse(object);
            response.add(warehouseVehicleResponse);
        }

        return response;
    }

    private WarehouseVehicleResponse setWarehouseVehicleResponse(Object[] object) {
        WarehouseVehicleResponse warehouseVehicleResponse = new WarehouseVehicleResponse();
        warehouseVehicleResponse.setId(String.valueOf(object[0]));
        warehouseVehicleResponse.setVehicleId(String.valueOf(object[1]));
        warehouseVehicleResponse.setWarehouseId(String.valueOf(object[2]));
        logger.info(String.valueOf(object[3]));
        warehouseVehicleResponse.setStartDate(String.valueOf(object[3]));
        warehouseVehicleResponse.setEndDate(String.valueOf(object[4]));
        warehouseVehicleResponse.setVehicleRegNo((String) object[5]);
        warehouseVehicleResponse.setVehicleLocation((String) object[6]);
        warehouseVehicleResponse.setWarehouseName((String) object[7]);
        warehouseVehicleResponse.setWarehouseAddress((String) object[8]);
        warehouseVehicleResponse.setWarehousepin((String) object[9]);

        return warehouseVehicleResponse;
    }

    public List<WarehouseVehicleResponse> getVehicleDetails(int warehouseId){
        List<Object[]> data = warehouseVehicleRepo.findByWarehouseId(warehouseId);
        if(data.isEmpty()){
            throw new DataNotFoundException("Data is not present.");
        }
        List<WarehouseVehicleResponse> response = new ArrayList<>();
        for(Object object[]: data){
            WarehouseVehicleResponse warehouseVehicleResponse = setWarehouseVehicleResponse(object);
            response.add(warehouseVehicleResponse);
        }

        return response;
    }

    public List<WarehouseVehicleResponse> getAvailableVehicleDetails(int warehouseId, String fromDate, String toDate) throws ParseException {
        List<Object[]> data = null;
        Date strDate;
        Date endDate;
        try {
            strDate = utilityService.convertDate(fromDate);
            endDate = utilityService.convertDate(toDate);
            data = warehouseVehicleRepo.findAvailableVehicleDetails(warehouseId,
                    strDate, endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(data.isEmpty()){
            throw new DataNotFoundException("Data is not present.");
        }
        List<WarehouseVehicleResponse> response = new ArrayList<>();
        for(Object object[]: data){
            WarehouseVehicleResponse warehouseVehicleResponse = setWarehouseVehicleResponse(object);
            if(utilityService.convertDate(warehouseVehicleResponse.getStartDate()).before(strDate))
                response.add(warehouseVehicleResponse);
        }

        return response;
    }

    public List<WarehouseVehicleResponse> getAvailableVehicleByVegicleId(int vegicleId, String fromDate, String toDate) throws ParseException {
        List<Object[]> data = null;
        Date strDate;
        Date endDate;
        try {
            strDate = utilityService.convertDate(fromDate);
            endDate = utilityService.convertDate(toDate);
            data = warehouseVehicleRepo.findAvailableVehicleByVehicleID(vegicleId,
                    strDate, endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(data.isEmpty()){
            throw new DataNotFoundException("Data is not present.");
        }
        List<WarehouseVehicleResponse> response = new ArrayList<>();
        for(Object object[]: data){
            WarehouseVehicleResponse warehouseVehicleResponse = setWarehouseVehicleResponse(object);
            if(utilityService.convertDate(warehouseVehicleResponse.getStartDate()).before(strDate))
                response.add(warehouseVehicleResponse);
        }

        return response;
    }
}
