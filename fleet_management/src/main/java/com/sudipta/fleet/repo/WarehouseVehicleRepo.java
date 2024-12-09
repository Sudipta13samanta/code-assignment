package com.sudipta.fleet.repo;

import com.sudipta.fleet.entity.WarehouseVehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WarehouseVehicleRepo extends JpaRepository<WarehouseVehicleEntity, Integer> {
    public WarehouseVehicleEntity findByVehicleId(int vehicle_id);

    @Query(value="SELECT wv.id, wv.vehicleId, wv.warehouseId, wv.startDate, wv.endDate, " +
            "v.regNo, v.location, w.name, w.address, w.pin FROM WarehouseVehicleEntity wv " +
            "LEFT JOIN VehicleEntity v on v.id=wv.vehicleId "+
            "LEFT JOIN WarehouseEntity w on w.id=wv.warehouseId "+
            //", VehicleEntity v, WarehouseEntity w " +
            "WHERE v.regNo= :regNo")
    public List<Object[]> findByVehicleRegNo(@Param("regNo") String regNo);

    @Query(value="SELECT wv.id, wv.vehicleId, wv.warehouseId, wv.startDate, wv.endDate, " +
            "v.regNo, v.location, w.name, w.address, w.pin FROM WarehouseVehicleEntity wv " +
            //", VehicleEntity v, WarehouseEntity w " +
            "LEFT JOIN VehicleEntity v on v.id=wv.vehicleId "+
            "LEFT JOIN WarehouseEntity w on w.id=wv.warehouseId "+
            "WHERE wv.warehouseId= :warehouseId and wv.endDate IS null")
    public List<Object[]> findAvailableVehicleDetails(@Param("warehouseId") int warehouseId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value="SELECT wv.id, wv.vehicleId, wv.warehouseId, wv.startDate, wv.endDate, " +
            "v.regNo, v.location, w.name, w.address, w.pin FROM WarehouseVehicleEntity wv " +
            //", VehicleEntity v, WarehouseEntity w " +
            "LEFT JOIN VehicleEntity v on v.id=wv.vehicleId "+
            "LEFT JOIN WarehouseEntity w on w.id=wv.warehouseId "+
            "WHERE wv.vehicleId= :vehicleId and wv.endDate IS null")
    public List<Object[]> findAvailableVehicleByVehicleID(@Param("vehicleId") int vehicleId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    public List<Object[]> findByWarehouseId(int warehouseId);

}
