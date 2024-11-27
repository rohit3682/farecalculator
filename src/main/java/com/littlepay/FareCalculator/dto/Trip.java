package com.littlepay.FareCalculator.dto;

import com.opencsv.bean.CsvBindByName;

public class Trip  {

    @CsvBindByName
    private Integer tripId;

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }
}
