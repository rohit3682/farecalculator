package com.littlepay.FareCalculator.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDateTime;

public class Trip  {

    @CsvBindByName(column = "ID")
    private Integer id;

    @CsvDate(value = " dd-MM-yyyy HH:mm:ss")
    @CsvBindByName(column = "DateTimeUTC")
    private LocalDateTime dateTimeUtc;

    @CsvBindByName(column = "TapType")
    private String tapType;

    @CsvBindByName(column = "StopId")
    private String stopId;

    @CsvBindByName(column = "CompanyId")
    private String companyId;

    @CsvBindByName(column = "BusID")
    private String busId;

    @CsvBindByName(column = "PAN")
    private String pan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTimeUtc() {
        return dateTimeUtc;
    }

    public void setDateTimeUtc(LocalDateTime dateTimeUtc) {
        this.dateTimeUtc = dateTimeUtc;
    }

    public String getTapType() {
        return tapType;
    }

    public void setTapType(String tapType) {
        this.tapType = tapType;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
