package com.sudipta.book.train_booking.repo;

import com.sudipta.book.train_booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
