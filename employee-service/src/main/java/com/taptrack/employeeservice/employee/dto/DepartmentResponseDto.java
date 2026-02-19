package com.taptrack.employeeservice.employee.dto;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class DepartmentResponseDto {

    private Long id;
    private String departmentCode;
    private String departmentName;
    private String location;
    private String departmentHeadId;
    private String description;

    public DepartmentResponseDto() {
    }

    public DepartmentResponseDto(Long id, String departmentCode, String departmentName, String location, String departmentHeadId, String description) {
        this.id = id;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.location = location;
        this.departmentHeadId = departmentHeadId;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartmentHeadId() {
        return departmentHeadId;
    }

    public void setDepartmentHeadId(String departmentHeadId) {
        this.departmentHeadId = departmentHeadId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DepartmentResponseDto{" +
                "id=" + id +
                ", departmentCode='" + departmentCode + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", location='" + location + '\'' +
                ", departmentHeadId='" + departmentHeadId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
