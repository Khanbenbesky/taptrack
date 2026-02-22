package com.taptrack.employeeservice.employee.dto;

public class EmployeeSummaryDto {

    private Long id;
    private String employeeId;
    private String fullName;
    private String email;
    private String designationName;
    private String departmentName;
    private Boolean isActive;

    public EmployeeSummaryDto() {
    }

    public EmployeeSummaryDto(Long id, String employeeId, String fullName, String email, String designationName, String departmentName, Boolean isActive) {
        this.id = id;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.designationName = designationName;
        this.departmentName = departmentName;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "EmployeeSummaryDto{" +
                "id=" + id +
                ", employeeId='" + employeeId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", designationName='" + designationName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
