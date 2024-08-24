package com.sudipta.book.train_booking.dto.response;

import com.sudipta.book.train_booking.dto.Section;
import com.sudipta.book.train_booking.dto.request.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserTicketDto {
    private String ticketId;
    private Section section;
    private String seatNo;
    private String fromLocation;
    private String toLocation;
    private String price;
    private UserDto userDto;
}
