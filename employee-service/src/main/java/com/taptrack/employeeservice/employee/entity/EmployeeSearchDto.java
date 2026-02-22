package com.taptrack.employeeservice.employee.entity;

public class EmployeeSearchDto {

    private Long departmentId;
    private Long designationId;
    private Long shiftId;
    private Boolean isActive;
    private String keyword;

    public EmployeeSearchDto() {
    }

    public EmployeeSearchDto(Long departmentId, Long designationId, Long shiftId, Boolean isActive, String keyword) {
        this.departmentId = departmentId;
        this.designationId = designationId;
        this.shiftId = shiftId;
        this.isActive = isActive;
        this.keyword = keyword;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "EmployeeSearchDto{" +
                "departmentId=" + departmentId +
                ", designationId=" + designationId +
                ", shiftId=" + shiftId +
                ", isActive=" + isActive +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
