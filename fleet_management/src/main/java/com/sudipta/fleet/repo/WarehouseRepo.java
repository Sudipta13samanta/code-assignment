package com.sudipta.fleet.repo;

import com.sudipta.fleet.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepo extends JpaRepository<WarehouseEntity, Integer> {
    public List<WarehouseEntity> findByPin(String pin);
    public WarehouseEntity findByName(String name);
    public WarehouseEntity findById(int id);
}
