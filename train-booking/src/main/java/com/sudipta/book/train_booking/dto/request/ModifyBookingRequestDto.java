package com.sudipta.book.train_booking.dto.request;

import com.sudipta.book.train_booking.dto.Section;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ModifyBookingRequestDto {
    @NonNull
    private String ticketId;
    @NonNull
    private String fromLocation;
    @NonNull
    private String toLocation;
    @NonNull
    private String email;
    @NonNull
    private Section section;
}
