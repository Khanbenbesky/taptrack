package com.taptrack.employeeservice.employee.dto;

public class DesignationRequestDto {

    private Long id;
    private String designationCode;
    private String designationName;
    private String grade;
    private String description;
    private Long departmentId;

    public DesignationRequestDto() {
    }

    public DesignationRequestDto(Long id, String designationCode, String designationName, String grade, String description, Long departmentId) {
        this.id = id;
        this.designationCode = designationCode;
        this.designationName = designationName;
        this.grade = grade;
        this.description = description;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignationCode() {
        return designationCode;
    }

    public void setDesignationCode(String designationCode) {
        this.designationCode = designationCode;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "DesignationRequestDto{" +
                "id=" + id +
                ", designationCode='" + designationCode + '\'' +
                ", designationName='" + designationName + '\'' +
                ", grade='" + grade + '\'' +
                ", description='" + description + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }
}
