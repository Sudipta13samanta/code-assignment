package com.sudipta.fleet.controller;

import com.sudipta.fleet.dto.BookingRequest;
import com.sudipta.fleet.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    private final Logger logger = LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<String> bookVehicle(@RequestBody BookingRequest bookingRequest){
        try{
            bookingService.bookVehicle(bookingRequest);
            return ResponseEntity.ok("updated");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/book")
    public ResponseEntity<Object> getBookingDetails(@RequestParam("regNo") String regNo){
        try{
            return ResponseEntity.ok().body(bookingService.getBookingDetails(regNo));
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
