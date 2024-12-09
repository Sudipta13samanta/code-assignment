package com.sudipta.fleet.repo;

import com.sudipta.fleet.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepo extends JpaRepository<VehicleEntity, Integer> {
    public VehicleEntity findByRegNo(String regNo);
    public VehicleEntity findById(int id);
}
