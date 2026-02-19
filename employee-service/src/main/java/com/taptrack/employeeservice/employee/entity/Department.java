package com.taptrack.employeeservice.employee.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SOLID — Single Responsibility:
 *   This class is ONLY responsible for department data.
 *   It does NOT handle employee logic, shift logic, or business rules.
 *
 * SOLID — Open/Closed:
 *   New fields (budget, costCenter) can be added without
 *   modifying existing employee or designation logic.
 */
@Entity
@Table(
        name = "departments",
        schema = "employee_schema",
        indexes = {
                @Index(name = "idx_dept_code", columnList = "department_code"),
                @Index(name = "idx_dept_active", columnList = "is_active")
        }
)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Short unique code — used in reporting, searches
     * Example: ENG, HR, FIN, OPS, SALES
     */
    @Column(name = "department_code", unique = true, nullable = false, length = 10)
    private String departmentCode;

    @Column(name = "department_name", nullable = false, length = 100)
    private String departmentName;

    /**
     * Location/office of this department
     * Example: Chennai, Pune, Bangalore, Hyderabad
     */
    @Column(name = "location", length = 100)
    private String location;

    /**
     * Self-reference: who heads this department
     * Optional — set after both department and employee are created
     */
    @Column(name = "department_head_id", length = 20)
    private String departmentHeadId;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ─── Relationships ────────────────────────────────────────────

    /**
     * ONE Department → MANY Employees
     *
     * mappedBy = "department" means Employee.department field owns the FK.
     * CascadeType.ALL is intentionally NOT used here —
     *   we never want deleting a department to delete employees.
     * fetch = LAZY: Don't load all employees just because we fetched a department.
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    /**
     * ONE Department → MANY Designations
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Designation> designations = new ArrayList<>();



    // ─── Business Methods (SRP: department knows its own rules) ───

    public int getActiveEmployeeCount() {
        return (int) employees.stream()
                .filter(e -> Boolean.TRUE.equals(e.getIsActive()))
                .count();
    }

    public boolean hasActiveEmployees() {
        return employees.stream().anyMatch(e -> Boolean.TRUE.equals(e.getIsActive()));
    }

    public Department() {
    }

    public Department(Long id, String departmentCode, String departmentName, String location, String departmentHeadId, String description, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, List<Employee> employees, List<Designation> designations) {
        this.id = id;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.location = location;
        this.departmentHeadId = departmentHeadId;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.employees = employees;
        this.designations = designations;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Designation> getDesignations() {
        return designations;
    }

    public void setDesignations(List<Designation> designations) {
        this.designations = designations;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(departmentCode, that.departmentCode) && Objects.equals(departmentName, that.departmentName) && Objects.equals(location, that.location) && Objects.equals(departmentHeadId, that.departmentHeadId) && Objects.equals(description, that.description) && Objects.equals(isActive, that.isActive) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(employees, that.employees) && Objects.equals(designations, that.designations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departmentCode, departmentName, location, departmentHeadId, description, isActive, createdAt, updatedAt, employees, designations);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentCode='" + departmentCode + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", location='" + location + '\'' +
                ", departmentHeadId='" + departmentHeadId + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", employees=" + employees +
                ", designations=" + designations +
                '}';
    }
}

