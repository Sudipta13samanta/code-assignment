package com.sudipta.book.train_booking.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@ToString
//@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
}
