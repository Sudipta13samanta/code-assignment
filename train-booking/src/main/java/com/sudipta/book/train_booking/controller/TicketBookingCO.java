package com.sudipta.book.train_booking.controller;

import com.sudipta.book.train_booking.dto.Section;
import com.sudipta.book.train_booking.dto.request.BookingRequestDto;
import com.sudipta.book.train_booking.dto.request.ModifyBookingRequestDto;
import com.sudipta.book.train_booking.dto.response.TicketDetailsDto;
import com.sudipta.book.train_booking.dto.response.UserTicketDto;
import com.sudipta.book.train_booking.exception.DataNotFoundException;
import com.sudipta.book.train_booking.service.TicketBookingService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class TicketBookingCO {

    private final Logger logger = Logger.getLogger("TrainBookingCO");
    @Autowired
    private TicketBookingService ticketBookingService;
    @PostMapping("/ticket")
    public ResponseEntity bookTicket(@Valid @RequestBody BookingRequestDto bookingRequestDto){
        logger.info(bookingRequestDto.toString());

        try{
            ticketBookingService.bookTicket(bookingRequestDto);
            return ResponseEntity.ok(HttpServletResponse.SC_OK);
        }catch (Exception exception){
            return ResponseEntity.status(HttpServletResponse.SC_EXPECTATION_FAILED).body(exception.getMessage());
        }
    }

    @GetMapping("/user_ticket")
    public ResponseEntity<TicketDetailsDto> receipt(@RequestParam String email){
        try{
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(ticketBookingService.getTicketDetails(email));
        }catch(DataNotFoundException ex){
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(null);
        }
    }

    @GetMapping("/seat-details")
    public ResponseEntity<List<UserTicketDto>> seatDetails(@RequestParam Section section){
        try{
            List<UserTicketDto> ticketDtoList = ticketBookingService.getSeatDetials(section);
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(ticketDtoList);
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam String email){
        if(ticketBookingService.deleteUser(email)){
            return ResponseEntity.status(HttpServletResponse.SC_OK).body("User is deleted.");
        }else{
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Data is not available for delete.");
        }

    }

    @PutMapping("/user-seat")
    public ResponseEntity<String> modifyUserSeat(@RequestBody ModifyBookingRequestDto modifyBookingRequestDto){
        try{
            ticketBookingService.modifyUserSeat(modifyBookingRequestDto);
            return ResponseEntity.status(HttpServletResponse.SC_OK).body("Successfully modify the seat");
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("User seat is not modified.");
        }
    }
}
