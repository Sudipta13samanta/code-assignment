package com.sudipta.fleet.repo;

import com.sudipta.fleet.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<BookingEntity, Integer> {

    public List<BookingEntity> findById(int id);

    public List<BookingEntity> findByVehicleId(int vehicleId);
}
