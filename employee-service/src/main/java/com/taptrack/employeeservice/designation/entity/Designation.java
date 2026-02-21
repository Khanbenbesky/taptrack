package com.taptrack.employeeservice.designation.entity;

import com.taptrack.employeeservice.department.entity.Department;
import com.taptrack.employeeservice.employee.entity.Employee;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLID — Single Responsibility:
 *   Only manages designation/role information.
 *   Does NOT contain any attendance, shift, or leave logic.
 *
 * SOLID — Dependency Inversion:
 *   Employee depends on Designation abstraction (via FK),
 *   not on a hardcoded string value.
 */
@Entity
@Table(
        name = "designations",
        schema = "employee_schema",
        indexes = {
                @Index(name = "idx_desig_code",  columnList = "designation_code"),
                @Index(name = "idx_desig_dept",  columnList = "department_id"),
                @Index(name = "idx_desig_grade", columnList = "grade")
        }
)
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Short code for the designation
     * Example: JD, SE, TL, PM, DM, VP
     */
    @Column(name = "designation_code", unique = true, nullable = false, length = 10)
    private String designationCode;

    @Column(name = "designation_name", nullable = false, length = 100)
    private String designationName;

    /**
     * Grade/Band level — used for payroll and leave quota rules
     * Example: C1 (Junior), C2 (Mid), C3 (Senior), C4 (Lead), C5 (Manager)
     */
    @Column(name = "grade", length = 10)
    private String grade;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ─── Relationships ────────────────────────────────────────────

    /**
     * MANY Designations → ONE Department
     *
     * A designation belongs to one department.
     * Example: "Tech Lead" belongs to "Engineering"
     *
     * JoinColumn: FK column name in designations table → department_id
     * fetch = LAZY: Don't load department data unless explicitly needed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "department_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_designation_department")
    )
    private Department department;

    /**
     * ONE Designation → MANY Employees
     *
     * CascadeType.ALL intentionally NOT used —
     *   deleting a designation should NEVER delete employees.
     */
    @OneToMany(mappedBy = "designation", fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    public Designation() {
    }

    public Designation(Long id, String designationCode, String designationName, String grade, String description, Boolean isActive, Boolean isDelete, LocalDateTime createdAt, LocalDateTime updatedAt, Department department, List<Employee> employees) {
        this.id = id;
        this.designationCode = designationCode;
        this.designationName = designationName;
        this.grade = grade;
        this.description = description;
        this.isActive = isActive;
        this.isDelete = isDelete;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.department = department;
        this.employees = employees;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Designation{" +
                "id=" + id +
                ", designationCode='" + designationCode + '\'' +
                ", designationName='" + designationName + '\'' +
                ", grade='" + grade + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", isDelete=" + isDelete +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", department=" + department +
                ", employees=" + employees +
                '}';
    }

    // ─── Convenience Methods ──────────────────────────────────────

    /**
     * Checks if this is a managerial designation
     * Managers can approve leaves and view team attendance
     */
    public boolean isManagerialLevel() {
        return grade != null && (
                grade.equalsIgnoreCase("C4") ||
                        grade.equalsIgnoreCase("C5") ||
                        grade.equalsIgnoreCase("C6")
        );
    }
}

