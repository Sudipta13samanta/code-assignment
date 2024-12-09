package com.sudipta.fleet.service;

import com.sudipta.fleet.dto.VehicleDto;
import com.sudipta.fleet.dto.WarehouseDto;
import com.sudipta.fleet.entity.VehicleEntity;
import com.sudipta.fleet.entity.WarehouseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UtilityService {

    public WarehouseDto setWarehouse(WarehouseEntity warehouseEntity){
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(warehouseEntity.getId());
        warehouseDto.setName(warehouseEntity.getName());
        warehouseDto.setAddress(warehouseEntity.getAddress());
        warehouseDto.setPin(warehouseEntity.getPin());
        return warehouseDto;
    }

    public WarehouseEntity setWarehouseDto(WarehouseDto warehouseDto){
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setId(warehouseDto.getId());
        warehouseEntity.setName(warehouseDto.getName());
        warehouseEntity.setAddress(warehouseDto.getAddress());
        warehouseEntity.setPin(warehouseDto.getPin());

        return warehouseEntity;
    }

    public VehicleDto setVehicle(VehicleEntity vehicleEntity){
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicleEntity.getId());
        vehicleDto.setLocation(vehicleEntity.getLocation());
        vehicleDto.setReg_no(vehicleEntity.getRegNo());
        return vehicleDto;
    }

    public VehicleEntity setWarehouseDto(VehicleDto warehouseDto){
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(warehouseDto.getId());
        vehicleEntity.setLocation(warehouseDto.getLocation());
        vehicleEntity.setRegNo(warehouseDto.getReg_no());

        return vehicleEntity;
    }

    public Date convertDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
}
