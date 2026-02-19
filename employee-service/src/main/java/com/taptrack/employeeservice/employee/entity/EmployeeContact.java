package com.taptrack.employeeservice.employee.entity;

import com.taptrack.employeeservice.employee.common.ContactType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SOLID — Single Responsibility:
 *   Manages ONLY employee contact and address information.
 *   Personal contacts, emergency contacts, and address
 *   are NOT mixed into the main Employee table.
 *
 * SOLID — Interface Segregation:
 *   Employee does not need to expose contact details to
 *   Attendance Service or Report Service. Those services
 *   call Employee Service only for what they need.
 */
@Entity
@Table(
    name = "employee_contacts",
    schema = "employee_schema",
    indexes = {
        @Index(name = "idx_contact_employee", columnList = "employee_id"),
        @Index(name = "idx_contact_type",     columnList = "contact_type")
    }
)
public class EmployeeContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Type of contact record
     * PERSONAL  → Employee's own address
     * EMERGENCY → Emergency contact person
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type", nullable = false, length = 20)
    private ContactType contactType;

    /**
     * Name of the contact person
     * For PERSONAL: employee's own name (usually)
     * For EMERGENCY: e.g. "Suresh Sharma (Father)"
     */
    @Column(name = "contact_name", nullable = false, length = 100)
    private String contactName;

    @Column(name = "contact_phone", nullable = false, length = 20)
    private String contactPhone;

    @Column(name = "contact_email", length = 150)
    private String contactEmail;

    /**
     * Relationship to employee — used for emergency contacts
     * Example: Father, Mother, Spouse, Sibling
     */
    @Column(name = "relationship", length = 50)
    private String relationship;

    @Column(name = "address_line1", length = 200)
    private String addressLine1;

    @Column(name = "address_line2", length = 200)
    private String addressLine2;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "country", length = 50)
    private String country = "India";

    @Column(name = "pincode", length = 10)
    private String pincode;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ─── Relationships ────────────────────────────────────────────

    /**
     * MANY Contacts → ONE Employee
     *
     * FetchType.LAZY: Employee data NOT loaded just because
     *   we fetched a contact — avoids unnecessary DB calls.
     *
     * Optional = false: every contact MUST belong to an employee.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "employee_id",
        nullable = false,
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_contact_employee")
    )
    private Employee employee;

    // ─── Convenience Methods ──────────────────────────────────────

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (addressLine1 != null) sb.append(addressLine1);
        if (addressLine2 != null) sb.append(", ").append(addressLine2);
        if (city != null)         sb.append(", ").append(city);
        if (state != null)        sb.append(", ").append(state);
        if (pincode != null)      sb.append(" - ").append(pincode);
        if (country != null)      sb.append(", ").append(country);
        return sb.toString();
    }

    public EmployeeContact() {
    }

    public EmployeeContact(Long id, ContactType contactType, String contactName, String contactPhone, String contactEmail, String relationship, String addressLine1, String addressLine2, String city, String state, String country, String pincode, Boolean isPrimary, LocalDateTime createdAt, Employee employee) {
        this.id = id;
        this.contactType = contactType;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.relationship = relationship;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.isPrimary = isPrimary;
        this.createdAt = createdAt;
        this.employee = employee;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeContact that = (EmployeeContact) o;
        return Objects.equals(id, that.id) && contactType == that.contactType && Objects.equals(contactName, that.contactName) && Objects.equals(contactPhone, that.contactPhone) && Objects.equals(contactEmail, that.contactEmail) && Objects.equals(relationship, that.relationship) && Objects.equals(addressLine1, that.addressLine1) && Objects.equals(addressLine2, that.addressLine2) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country) && Objects.equals(pincode, that.pincode) && Objects.equals(isPrimary, that.isPrimary) && Objects.equals(createdAt, that.createdAt) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactType, contactName, contactPhone, contactEmail, relationship, addressLine1, addressLine2, city, state, country, pincode, isPrimary, createdAt, employee);
    }

    @Override
    public String toString() {
        return "EmployeeContact{" +
                "id=" + id +
                ", contactType=" + contactType +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", relationship='" + relationship + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                ", isPrimary=" + isPrimary +
                ", createdAt=" + createdAt +
                ", employee=" + employee +
                '}';
    }
}
