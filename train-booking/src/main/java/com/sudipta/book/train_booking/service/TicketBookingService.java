package com.sudipta.book.train_booking.service;

import com.sudipta.book.train_booking.dto.request.ModifyBookingRequestDto;
import com.sudipta.book.train_booking.dto.response.TicketDetailsDto;
import com.sudipta.book.train_booking.dto.response.TicketDto;
import com.sudipta.book.train_booking.dto.response.UserTicketDto;
import com.sudipta.book.train_booking.exception.DataNotFoundException;
import com.sudipta.book.train_booking.exception.TicketNotAvaiableException;
import com.sudipta.book.train_booking.dto.Section;
import com.sudipta.book.train_booking.dto.request.BookingRequestDto;
import com.sudipta.book.train_booking.dto.request.UserDto;
import com.sudipta.book.train_booking.entity.Ticket;
import com.sudipta.book.train_booking.entity.User;
import com.sudipta.book.train_booking.repo.TicketRepo;
import com.sudipta.book.train_booking.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketBookingService {

    private static int noOfTicketSectionA=20;
    private static int noOfTicketSectionB=20;
    private final String ticketNoSectionA="SEC_A_";
    private final String ticketNoSectionB="SEC_B_";
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Transactional
    public void bookTicket(BookingRequestDto bookingRequestDto){
        User user = getUser(bookingRequestDto.getUserDto());
        Ticket ticket = getTicketDetails(bookingRequestDto);
        ticket.setUser(user);
        try{
            ticketRepo.save(ticket);
        } catch (Exception exception){
            exception.getStackTrace();
            throw new RuntimeException(exception.getLocalizedMessage());
        }
    }

    public TicketDetailsDto getTicketDetails(String email){
        User user = userRepo.findByEmail(email);
        List<Ticket> tickets = ticketRepo.findByUser(user);
        return setTicketDetails(tickets);
    }

    public List<UserTicketDto> getSeatDetials(Section section){
        List<Ticket> tickets = ticketRepo.findBySection(section);
        if(tickets.isEmpty()){
            throw new DataNotFoundException("400", "Data not available for this section.");
        }
        List<UserTicketDto> ticketDetailsDtoList = new ArrayList<>();
        for(Ticket ticket:tickets){
            UserDto userDto = getUserDto(ticket);
            UserTicketDto userTicketDto = new UserTicketDto();
            userTicketDto.setTicketId(ticket.getTicket_id());
            userTicketDto.setFromLocation(ticket.getFromLocation());
            userTicketDto.setToLocation(ticket.getToLocation());
            userTicketDto.setSection(ticket.getSection());
            userTicketDto.setSeatNo(ticket.getSeatNo());
            userTicketDto.setUserDto(userDto);
            ticketDetailsDtoList.add(userTicketDto);
        }
        return ticketDetailsDtoList;
    }

    public boolean deleteUser(String email){
        User user = userRepo.findByEmail(email);
        if(user != null){
            List<Ticket> tickets = ticketRepo.findByUser(user);
            userRepo.delete(user);
            for(Ticket ticket:tickets){
                if(ticket.getSection()==Section.SECTION_A){
                    noOfTicketSectionA++;
                }
                if(ticket.getSection()==Section.SECTION_B){
                    noOfTicketSectionB++;
                }
            }
            return true;
        }else{
            return false;
        }
    }

    public void modifyUserSeat(ModifyBookingRequestDto modifyBookingRequestDto){
        Optional<Ticket> ticket = ticketRepo.findById(modifyBookingRequestDto.getTicketId());
        if(ticket.isPresent()){
            Ticket newTicket = ticket.get();
            newTicket.setFromLocation(modifyBookingRequestDto.getFromLocation());
            newTicket.setToLocation(modifyBookingRequestDto.getToLocation());
            allocateSeat(modifyBookingRequestDto, newTicket);
            ticketRepo.save(newTicket);
        } else{
            throw new TicketNotAvaiableException("500", "Ticket is not available, can not modify the ticket.");
        }
    }

    private void allocateSeat(ModifyBookingRequestDto modifyBookingRequestDto, Ticket newTicket) {
        StringBuilder seat = new StringBuilder();
        if(newTicket.getSection()==Section.SECTION_A &&
                modifyBookingRequestDto.getSection()==Section.SECTION_B){
            if(noOfTicketSectionB > 0){
                seat.append(ticketNoSectionB).append(noOfTicketSectionB);
                newTicket.setSection(modifyBookingRequestDto.getSection());
                newTicket.setSeatNo(seat.toString());
                noOfTicketSectionB--;
                noOfTicketSectionA++;
            }else{
                throw new TicketNotAvaiableException("500", "Section A ticket not available.");
            }
        }else if(newTicket.getSection()==Section.SECTION_B &&
                modifyBookingRequestDto.getSection()==Section.SECTION_A){
            if(noOfTicketSectionA > 0){
                seat.append(ticketNoSectionA).append(noOfTicketSectionA);
                newTicket.setSection(modifyBookingRequestDto.getSection());
                newTicket.setSeatNo(seat.toString());
                noOfTicketSectionA--;
                noOfTicketSectionB++;
            }else{
                throw new TicketNotAvaiableException("500", "Section A ticket not available.");
            }
        } else{
            throw new TicketNotAvaiableException("500", "Requested ticket in same Section.");
        }
    }

    private Ticket getTicketDetails(BookingRequestDto bookingRequestDto) {
        Ticket ticket = new Ticket();
        StringBuilder seatNo = new StringBuilder();
        ticket.setFromLocation(bookingRequestDto.getFromLocation());
        ticket.setToLocation(bookingRequestDto.getToLocation());
        ticket.setSection(bookingRequestDto.getSection());
        if(bookingRequestDto.getSection() == Section.SECTION_A){
            if(noOfTicketSectionA > 0 && noOfTicketSectionA <=20) {
                seatNo.append(ticketNoSectionA).append(noOfTicketSectionA);
                noOfTicketSectionA--;
            }else{
                throw new TicketNotAvaiableException("500", "Section A ticket not available.");
            }
        }else if(bookingRequestDto.getSection() == Section.SECTION_B){
            if(noOfTicketSectionB > 0 && noOfTicketSectionB <=20) {
                seatNo.append(ticketNoSectionB).append(noOfTicketSectionB);
                noOfTicketSectionB--;
            }else{
                throw new TicketNotAvaiableException("500", "Section B ticket not available.");
            }
        }
        ticket.setSeatNo(seatNo.toString());
        return ticket;
    }

    private User getUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        return user;
    }


    private TicketDetailsDto setTicketDetails(List<Ticket> tickets) {
        TicketDetailsDto ticketDetailsDto = new TicketDetailsDto();
        UserDto userDto = null;
        if(tickets != null && !tickets.isEmpty()) {
            if(tickets.get(0).getUser()!=null) {
                Ticket ticket = tickets.get(0);
                userDto = getUserDto(ticket);
                ticketDetailsDto.setUser(userDto);
            }else{
                throw new DataNotFoundException("400", "User is not available.");
            }
            List<TicketDto> ticketDtoList = new ArrayList<>();
            for(Ticket ticket:tickets){
                TicketDto ticketDto = new TicketDto();
                ticketDto.setTicketId(ticket.getTicket_id());
                ticketDto.setFromLocation(ticket.getFromLocation());
                ticketDto.setToLocation(ticket.getToLocation());
                ticketDto.setSection(ticket.getSection());
                ticketDto.setSeatNo(ticket.getSeatNo());
                ticketDtoList.add(ticketDto);
            }
            ticketDetailsDto.setTicketDtoList(ticketDtoList);
        }else{
            throw new DataNotFoundException("400", "Ticket is not available.");
        }
        return ticketDetailsDto;
    }

    private static UserDto getUserDto(Ticket ticket) {
        UserDto userDto;
        userDto = new UserDto(ticket.getUser().getFirstName(),
                ticket.getUser().getLastName(), ticket.getUser().getEmail());
        return userDto;
    }
}
