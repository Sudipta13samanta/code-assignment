package com.sudipta.fleet.service;

import com.sudipta.fleet.dto.WarehouseDto;
import com.sudipta.fleet.entity.WarehouseEntity;
import com.sudipta.fleet.exception.DataNotFoundException;
import com.sudipta.fleet.repo.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepo warehouseRepo;
    @Autowired
    private UtilityService utilityService;
    private static int uniqueId=1;

    public WarehouseDto addWarehouse(WarehouseDto warehouseDto){
        warehouseDto.setId(uniqueId++);
        warehouseRepo.save(utilityService.setWarehouseDto(warehouseDto));
        return warehouseDto;
    }

    public WarehouseDto updateWarehouse(WarehouseDto warehouseDto){
        WarehouseEntity warehouseEntity = warehouseRepo.findByName(warehouseDto.getName());
        if(warehouseEntity != null){
            warehouseEntity.setPin(warehouseDto.getPin());
            warehouseEntity.setName(warehouseDto.getName());
            warehouseEntity.setAddress(warehouseDto.getAddress());
            warehouseRepo.save(warehouseEntity);
        }else{
                throw new DataNotFoundException("Vehicle data is not present for given name.");
        }
        return warehouseDto;
    }

    public List<WarehouseDto> getWarehouse(String pin){
        List<WarehouseDto> warehouseDtoList = new ArrayList<>();
        List<WarehouseEntity> warehouseEntities = warehouseRepo.findByPin(pin);
        if(warehouseEntities ==null || warehouseEntities.isEmpty()){
            throw new DataNotFoundException("Warehouse is not available in the given pin.");
        }else{
            for(WarehouseEntity warehouseEntity:warehouseEntities){
                warehouseDtoList.add(utilityService.setWarehouse(warehouseEntity));
            }
            return warehouseDtoList;
        }

    }

    public List<WarehouseDto> getAllWarehouse(){
        List<WarehouseEntity> warehouseEntities = warehouseRepo.findAll();
        List<WarehouseDto> warehouseDtoList = new ArrayList<>();
        if(!warehouseEntities.isEmpty()){
            for(WarehouseEntity warehouseEntity: warehouseEntities){
                warehouseDtoList.add(utilityService.setWarehouse(warehouseEntity));
            }
        }

        return warehouseDtoList;
    }


}
