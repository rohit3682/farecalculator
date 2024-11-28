package com.littlepay.FareCalculator.dto;

import java.time.LocalDateTime;

public class BusTripSummary {

    private String busId;
    private LocalDateTime started;
    private LocalDateTime finished;
    private long durationSeconds;
    private String fromStopId;
    private String toStopId;
    private double chargeAmt;
    private String companyId;
    private String pan;
    private String status;

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public void setFromStopId(String fromStopId) {
        this.fromStopId = fromStopId;
    }

    public String getToStopId() {
        return toStopId;
    }

    public void setToStopId(String toStopId) {
        this.toStopId = toStopId;
    }

    public double getChargeAmt() {
        return chargeAmt;
    }

    public void setChargeAmt(double chargeAmt) {
        this.chargeAmt = chargeAmt;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusId() {
        return busId;
    }
    
    public void setBusId(String busId) {
        this.busId = busId;
    }
}
