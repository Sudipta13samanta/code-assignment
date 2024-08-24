package com.sudipta.book.train_booking.repo;

import com.sudipta.book.train_booking.dto.Section;
import com.sudipta.book.train_booking.entity.Ticket;
import com.sudipta.book.train_booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
    public List<Ticket> findByUser(User user);
    public List<Ticket> findBySection(Section section);
}
