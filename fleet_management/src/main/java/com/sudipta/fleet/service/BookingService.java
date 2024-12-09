package com.sudipta.fleet.service;

import com.sudipta.fleet.dto.BookingRequest;
import com.sudipta.fleet.dto.BookingResponse;
import com.sudipta.fleet.entity.BookingEntity;
import com.sudipta.fleet.entity.VehicleEntity;
import com.sudipta.fleet.exception.DataNotFoundException;
import com.sudipta.fleet.repo.BookingRepo;
import com.sudipta.fleet.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private VehicleRepo vehicleRepo;
    @Autowired
    private UtilityService utilityService;

    public void bookVehicle(BookingRequest bookingRequest){
        VehicleEntity vehicleEntity = vehicleRepo.findByRegNo(bookingRequest.getRegNo());
        Date strDate;
        Date endDate;
        try {
            strDate = utilityService.convertDate(bookingRequest.getFromDate());
            endDate = utilityService.convertDate(bookingRequest.getToDate());
            List<BookingEntity> bookingEntityList = bookingRepo.findByVehicleId(vehicleEntity.getId());
            boolean isAvailable=true;
            if(bookingEntityList!=null && bookingEntityList.isEmpty()){
                for(BookingEntity bookingEntity: bookingEntityList){
                    if(bookingEntity.getEndDate().after(strDate)){
                        isAvailable=false;
                        break;
                    }
                }
            }else{
                throw new RuntimeException("Data is not available");
            }


            if(isAvailable){
                BookingEntity bookingEntity = new BookingEntity();
                bookingEntity.setVehicleId(vehicleEntity.getId());
                bookingEntity.setUser(bookingRequest.getUserName());
                bookingEntity.setStartDate(strDate);
                bookingEntity.setEndDate(endDate);
                bookingRepo.save(bookingEntity);
            }else{
                throw new RuntimeException("Booking is not available");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<BookingResponse> getBookingDetails(String regNo){
        VehicleEntity vehicleEntity = vehicleRepo.findByRegNo(regNo);
        if(vehicleEntity==null){
            throw new DataNotFoundException("VehicleEntity is not present.");
        }
        List<BookingEntity> bookingEntityList = bookingRepo.findByVehicleId(vehicleEntity.getId());
        if(bookingEntityList.isEmpty()){
            throw new DataNotFoundException("BookingEntity is not present.");
        }
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for(BookingEntity bookingEntity:bookingEntityList){
            BookingResponse bookingResponse = new BookingResponse();
            bookingResponse.setId(bookingEntity.getId());
            bookingResponse.setVehicleId(vehicleEntity.getId());
            bookingResponse.setRegNo(vehicleEntity.getRegNo());
            bookingResponse.setUser(bookingEntity.getUser());
            bookingResponse.setStartDate(bookingEntity.getStartDate());
            bookingResponse.setEndDate(bookingEntity.getEndDate());

            bookingResponseList.add(bookingResponse);
        }

        return bookingResponseList;
    }
}
