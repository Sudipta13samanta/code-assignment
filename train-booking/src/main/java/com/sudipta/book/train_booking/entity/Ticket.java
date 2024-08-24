package com.sudipta.book.train_booking.entity;

import com.sudipta.book.train_booking.dto.Section;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@ToString
//@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticket_id;
    private String fromLocation;
    private String toLocation;
    @OneToOne
    @Cascade(CascadeType.ALL)
    private User user;
    private Section section;
    private String seatNo;
}
