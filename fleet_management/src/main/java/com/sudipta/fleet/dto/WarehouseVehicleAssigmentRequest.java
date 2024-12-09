package com.sudipta.fleet.dto;

import java.util.Date;

public class WarehouseVehicleAssigmentRequest {
    private int warehoseId;
    private String regno;
    private String startDate;
    private String endDate;

    public int getWarehoseId() {
        return warehoseId;
    }

    public void setWarehoseId(int warehoseId) {
        this.warehoseId = warehoseId;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
