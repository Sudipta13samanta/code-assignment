package com.sudipta.fleet.service;

import com.sudipta.fleet.dto.VehicleDto;
import com.sudipta.fleet.entity.VehicleEntity;
import com.sudipta.fleet.exception.DataNotFoundException;
import com.sudipta.fleet.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

    private static int uniqueId=1;
    @Autowired
    private VehicleRepo vehicleRepo;
    @Autowired
    private UtilityService utilityService;

    public VehicleDto addVehicle(VehicleDto vehicleDto){
        vehicleDto.setId(uniqueId++);
        vehicleRepo.save(utilityService.setWarehouseDto(vehicleDto));
        return vehicleDto;
    }

    public VehicleDto updateVehicle(VehicleDto vehicleDto){
        VehicleEntity vehicleEntity = vehicleRepo.findByRegNo(vehicleDto.getReg_no());
        if(vehicleEntity != null){
            vehicleEntity.setRegNo(vehicleDto.getReg_no());
            vehicleEntity.setLocation(vehicleDto.getLocation());
            vehicleRepo.save(vehicleEntity);
            return utilityService.setVehicle(vehicleEntity);
        }else{
            throw new DataNotFoundException("Vehicle data is not present for given reg no.");
        }
    }

    public VehicleDto getVehicle(String regNo){
        VehicleEntity vehicleEntity = vehicleRepo.findByRegNo(regNo);
        if(vehicleEntity == null){
            throw new DataNotFoundException("Vehicle data is not present for given reg no.");
        }

        return utilityService.setVehicle(vehicleEntity);
    }

}
