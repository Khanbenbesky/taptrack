package com.taptrack.employeeservice.employee.dto;

import com.taptrack.employeeservice.employee.common.Gender;

import java.time.LocalDate;

public class EmployeeRequestDto {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfJoining;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String bloodGroup;
    private String profilePhotoUrl;
    private Boolean isActive;
    private Long departmentId;
    private Long designationId;
    private Long shiftId;
    private Long managerId;

    public EmployeeRequestDto() {
    }

    public EmployeeRequestDto(String employeeId, String firstName, String lastName, String email, String phone, LocalDate dateOfJoining, LocalDate dateOfBirth, Gender gender, String bloodGroup, String profilePhotoUrl, Boolean isActive, Long departmentId, Long designationId, Long shiftId, Long managerId) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfJoining = dateOfJoining;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.profilePhotoUrl = profilePhotoUrl;
        this.isActive = isActive;
        this.departmentId = departmentId;
        this.designationId = designationId;
        this.shiftId = shiftId;
        this.managerId = managerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "EmployeeRequestDTO{" +
                "employeeId='" + employeeId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", profilePhotoUrl='" + profilePhotoUrl + '\'' +
                ", isActive=" + isActive +
                ", departmentId=" + departmentId +
                ", designationId=" + designationId +
                ", shiftId=" + shiftId +
                ", managerId=" + managerId +
                '}';
    }
}
