package com.sudipta.book.train_booking.dto.request;

import com.sudipta.book.train_booking.dto.Section;
import com.sudipta.book.train_booking.exception.DataNotFoundException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class BookingRequestDto {
    @NonNull
    private String fromLocation;
    @NonNull
    private String toLocation;
    @NonNull
    private UserDto userDto;
    @NonNull
    private Section section;

}
