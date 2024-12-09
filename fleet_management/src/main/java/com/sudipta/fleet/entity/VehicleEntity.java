package com.sudipta.fleet.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    private int id;
    private String regNo;
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
