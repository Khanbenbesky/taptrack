package com.taptrack.employeeservice.employee.entity;

import com.taptrack.employeeservice.employee.common.Gender;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "employee",
        schema = "employee_schema",
        indexes = {
                @Index(name = "idx_emp_employee_id",  columnList = "employee_id",   unique = true),
                @Index(name = "idx_emp_email",        columnList = "email",         unique = true),
                @Index(name = "idx_emp_department",   columnList = "department_id"),
                @Index(name = "idx_emp_shift",        columnList = "shift_id"),
                @Index(name = "idx_emp_manager",      columnList = "manager_id"),
                @Index(name = "idx_emp_active",       columnList = "is_active")
        }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", unique = true, nullable = false, length = 20)
    private String employeeId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "department_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_employee_department")
    )
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "designation_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_employee_designation")
    )
    private Designation designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "shift_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_employee_shift")
    )
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "manager_id",
            foreignKey = @ForeignKey(name = "fk_employee_manager")
    )
    private Employee manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Employee> subordinates = new ArrayList<>();

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<EmployeeContact> contacts = new ArrayList<>();

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<EmployeeDocument> documents = new ArrayList<>();


    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Delegates to Shift entity — no duplicate logic
     * SOLID: Employee does NOT know late rules, Shift does
     */
    public boolean isLateArrival(java.time.LocalTime tapTime) {
        return shift != null && shift.isLate(tapTime);
    }

    /**
     * Convenience: get shift start time via relationship
     * No raw time stored in Employee — always from Shift table
     */
    public java.time.LocalTime getShiftStart() {
        return shift != null ? shift.getShiftStart() : null;
    }

    public java.time.LocalTime getShiftEnd() {
        return shift != null ? shift.getShiftEnd() : null;
    }

    public String getDepartmentName() {
        return department != null ? department.getDepartmentName() : null;
    }

    public String getDesignationName() {
        return designation != null ? designation.getDesignationName() : null;
    }

    /**
     * Add contact with bidirectional sync
     */
    public void addContact(EmployeeContact contact) {
        contacts.add(contact);
        contact.setEmployee(this);
    }

    /**
     * Add document with bidirectional sync
     */
    public void addDocument(EmployeeDocument document) {
        documents.add(document);
        document.setEmployee(this);
    }

    public Employee() {
    }

    public Employee(Long id, String employeeId, String firstName, String lastName, String email, String phone, LocalDate dateOfJoining, LocalDate dateOfBirth, Gender gender, String bloodGroup, String profilePhotoUrl, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, Department department, Designation designation, Shift shift, Employee manager, List<Employee> subordinates, List<EmployeeContact> contacts, List<EmployeeDocument> documents) {
        this.id = id;
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.department = department;
        this.designation = designation;
        this.shift = shift;
        this.manager = manager;
        this.subordinates = subordinates;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public List<EmployeeContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<EmployeeContact> contacts) {
        this.contacts = contacts;
    }

    public List<EmployeeDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<EmployeeDocument> documents) {
        this.documents = documents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(employeeId, employee.employeeId) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(email, employee.email) && Objects.equals(phone, employee.phone) && Objects.equals(dateOfJoining, employee.dateOfJoining) && Objects.equals(dateOfBirth, employee.dateOfBirth) && gender == employee.gender && Objects.equals(bloodGroup, employee.bloodGroup) && Objects.equals(profilePhotoUrl, employee.profilePhotoUrl) && Objects.equals(isActive, employee.isActive) && Objects.equals(createdAt, employee.createdAt) && Objects.equals(updatedAt, employee.updatedAt) && Objects.equals(department, employee.department) && Objects.equals(designation, employee.designation) && Objects.equals(shift, employee.shift) && Objects.equals(manager, employee.manager) && Objects.equals(subordinates, employee.subordinates) && Objects.equals(contacts, employee.contacts) && Objects.equals(documents, employee.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, firstName, lastName, email, phone, dateOfJoining, dateOfBirth, gender, bloodGroup, profilePhotoUrl, isActive, createdAt, updatedAt, department, designation, shift, manager, subordinates, contacts, documents);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeId='" + employeeId + '\'' +
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
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", department=" + department +
                ", designation=" + designation +
                ", shift=" + shift +
                ", manager=" + manager +
                ", subordinates=" + subordinates +
                ", contacts=" + contacts +
                ", documents=" + documents +
                '}';
    }
}
