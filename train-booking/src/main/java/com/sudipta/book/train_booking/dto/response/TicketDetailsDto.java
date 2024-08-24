package com.sudipta.book.train_booking.dto.response;

import com.sudipta.book.train_booking.dto.Section;
import com.sudipta.book.train_booking.dto.request.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TicketDetailsDto {
    private UserDto user;
    private List<TicketDto> ticketDtoList;
}
