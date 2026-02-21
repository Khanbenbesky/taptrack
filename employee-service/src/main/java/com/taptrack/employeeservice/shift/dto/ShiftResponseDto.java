package com.taptrack.employeeservice.shift.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShiftResponseDto {

    private Long id;
    private String shiftCode;
    private String shiftName;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;
    private Integer graceMinutes;
    private BigDecimal workingHours;
    private Boolean isNightShift;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public ShiftResponseDto() {
    }

    public ShiftResponseDto(Long id, String shiftCode, String shiftName, LocalTime shiftStart, LocalTime shiftEnd, Integer graceMinutes, BigDecimal workingHours, Boolean isNightShift, Boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.shiftCode = shiftCode;
        this.shiftName = shiftName;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.graceMinutes = graceMinutes;
        this.workingHours = workingHours;
        this.isNightShift = isNightShift;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ShiftResponseDto{" +
                "id=" + id +
                ", shiftCode='" + shiftCode + '\'' +
                ", shiftName='" + shiftName + '\'' +
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                ", graceMinutes=" + graceMinutes +
                ", workingHours=" + workingHours +
                ", isNightShift=" + isNightShift +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}
