package com.taptrack.employeeservice.employee.dto;

import com.taptrack.employeeservice.employee.common.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EmployeeResponseDto {

    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dateOfJoining;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String bloodGroup;
    private String profilePhotoUrl;
    private Boolean isActive;
    private String departmentName;
    private String designationName;
    private String shiftName;
    private String managerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EmployeeContactResponseDto> contacts;
    private List<EmployeeDocumentResponseDto> documents;

    public EmployeeResponseDto() {
    }

    public EmployeeResponseDto(Long id, String employeeId, String firstName, String lastName, String fullName, String email, String phone, LocalDate dateOfJoining, LocalDate dateOfBirth, Gender gender, String bloodGroup, String profilePhotoUrl, Boolean isActive, String departmentName, String designationName, String shiftName, String managerName, LocalDateTime createdAt, LocalDateTime updatedAt, List<EmployeeContactResponseDto> contacts, List<EmployeeDocumentResponseDto> documents) {
        this.id = id;
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dateOfJoining = dateOfJoining;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.profilePhotoUrl = profilePhotoUrl;
        this.isActive = isActive;
        this.departmentName = departmentName;
        this.designationName = designationName;
        this.shiftName = shiftName;
        this.managerName = managerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.contacts = contacts;
        this.documents = documents;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<EmployeeContactResponseDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<EmployeeContactResponseDto> contacts) {
        this.contacts = contacts;
    }

    public List<EmployeeDocumentResponseDto> getDocuments() {
        return documents;
    }

    public void setDocuments(List<EmployeeDocumentResponseDto> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "EmployeeResponseDto{" +
                "id=" + id +
                ", employeeId='" + employeeId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", profilePhotoUrl='" + profilePhotoUrl + '\'' +
                ", isActive=" + isActive +
                ", departmentName='" + departmentName + '\'' +
                ", designationName='" + designationName + '\'' +
                ", shiftName='" + shiftName + '\'' +
                ", managerName='" + managerName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", contacts=" + contacts +
                ", documents=" + documents +
                '}';
    }
}
