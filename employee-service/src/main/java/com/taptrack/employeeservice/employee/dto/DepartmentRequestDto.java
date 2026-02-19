package com.taptrack.employeeservice.employee.dto;

public class DepartmentRequestDto {

    private String departmentCode;
    private String departmentName;
    private String location;
    private String departmentHeadId;
    private String description;

    public DepartmentRequestDto() {
    }

    public DepartmentRequestDto(String departmentCode, String departmentName, String location, String departmentHeadId, String description) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.location = location;
        this.departmentHeadId = departmentHeadId;
        this.description = description;
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
                ", departmentCode='" + departmentCode + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", location='" + location + '\'' +
                ", departmentHeadId='" + departmentHeadId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
