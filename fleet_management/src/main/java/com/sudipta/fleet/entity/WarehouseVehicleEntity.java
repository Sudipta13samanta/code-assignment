package com.sudipta.fleet.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "warehouse_vehicle")
public class WarehouseVehicleEntity {
    @Id
    private int id;
    private int vehicleId;
    private int warehouseId;
    private Date startDate;
    private Date endDate;

    @Override
    public String toString() {
        return "WarehouseVehicleEntity{" +
                "id=" + id +
                ", vehicleId='" + vehicleId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", start_date=" + startDate +
                ", end_date=" + endDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
