package com.taptrack.employeeservice.shift.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public class ShiftRequestDto {

    private String shiftCode;
    private String shiftName;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;
    private Integer graceMinutes;
    private BigDecimal workingHours;
    private Boolean isNightShift;
    private String description;

    public ShiftRequestDto() {
    }

    public ShiftRequestDto(String shiftCode, String shiftName, LocalTime shiftStart, LocalTime shiftEnd, Integer graceMinutes, BigDecimal workingHours, Boolean isNightShift, String description) {
        this.shiftCode = shiftCode;
        this.shiftName = shiftName;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.graceMinutes = graceMinutes;
        this.workingHours = workingHours;
        this.isNightShift = isNightShift;
        this.description = description;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public Integer getGraceMinutes() {
        return graceMinutes;
    }

    public void setGraceMinutes(Integer graceMinutes) {
        this.graceMinutes = graceMinutes;
    }

    public BigDecimal getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(BigDecimal workingHours) {
        this.workingHours = workingHours;
    }

    public Boolean getNightShift() {
        return isNightShift;
    }

    public void setNightShift(Boolean nightShift) {
        isNightShift = nightShift;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ShiftRequestDto{" +
                "shiftCode='" + shiftCode + '\'' +
                ", shiftName='" + shiftName + '\'' +
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                ", graceMinutes=" + graceMinutes +
                ", workingHours=" + workingHours +
                ", isNightShift=" + isNightShift +
                ", description='" + description + '\'' +
                '}';
    }
}
