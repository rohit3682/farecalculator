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
}
