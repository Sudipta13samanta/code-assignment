package com.sudipta.book.train_booking.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
}
