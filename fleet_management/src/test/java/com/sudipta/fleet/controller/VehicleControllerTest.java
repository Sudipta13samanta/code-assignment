package com.sudipta.fleet.controller;


import com.sudipta.fleet.dto.VehicleDto;
import com.sudipta.fleet.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


public class VehicleControllerTest {

    private VehicleController vehicleController;

    private VehicleDto vehicleDto;
    VehicleService vehicleService;

    @BeforeEach
    public void setup(){
        vehicleService = mock(VehicleService.class);
        vehicleController = new VehicleController(vehicleService);
    }

    @Test
    public void addVehicle_returnSuccess(){
        setDto();
        when(vehicleService.addVehicle(vehicleDto)).thenReturn(vehicleDto);
        ResponseEntity<Object> objectResponseEntity =  vehicleController.addVehicle(vehicleDto);
        assertNotNull(objectResponseEntity);
    }

    private void setDto() {

        vehicleDto = new VehicleDto();
        vehicleDto.setReg_no("test");
        vehicleDto.setLocation("test");
        vehicleDto.setId(1);
    }

    @Test
    public void testAddVehicle_whenMisssingDate_returnException(){
        ResponseEntity<Object> objectResponseEntity =  vehicleController.addVehicle(vehicleDto);
        assertEquals(objectResponseEntity.getStatusCode(), HttpStatusCode.valueOf(500));
    }

    @Test
    public void testUpdateVehicle_returnObject(){
        setDto();
        VehicleDto responseVehicle= mock(VehicleDto.class);
        when(vehicleService.updateVehicle(vehicleDto)).thenReturn(responseVehicle);
        ResponseEntity<Object> response = vehicleController.updateVehicle(vehicleDto);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
    }

    @Test
    public void testUpdateVehicle_whenMisssingDate_returnException(){
        ResponseEntity<Object> objectResponseEntity =  vehicleController.updateVehicle(null);
        assertEquals(objectResponseEntity.getStatusCode(), HttpStatusCode.valueOf(500));
    }
}
