package com.taptrack.employeeservice.employee.dto;

import com.taptrack.employeeservice.employee.common.ContactType;

import java.time.LocalDateTime;

public class EmployeeContactResponseDto {

    private Long id;
    private ContactType contactType;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String relationship;
    private String address;
    private Boolean isPrimary;
    private LocalDateTime createdAt;

    public EmployeeContactResponseDto() {
    }

    public EmployeeContactResponseDto(Long id, ContactType contactType, String contactName, String contactPhone, String contactEmail, String relationship, String address, Boolean isPrimary, LocalDateTime createdAt) {
        this.id = id;
        this.contactType = contactType;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.relationship = relationship;
        this.address = address;
        this.isPrimary = isPrimary;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "EmployeeContactResponseDto{" +
                "id=" + id +
                ", contactType=" + contactType +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", relationship='" + relationship + '\'' +
                ", address='" + address + '\'' +
                ", isPrimary=" + isPrimary +
                ", createdAt=" + createdAt +
                '}';
    }
}
