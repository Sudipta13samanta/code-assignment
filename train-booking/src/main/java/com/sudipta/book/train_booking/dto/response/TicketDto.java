package com.sudipta.book.train_booking.dto.response;

import com.sudipta.book.train_booking.dto.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketDto {
    private String ticketId;
    private Section section;
    private String seatNo;
    private String fromLocation;
    private String toLocation;
    private String price;
}
