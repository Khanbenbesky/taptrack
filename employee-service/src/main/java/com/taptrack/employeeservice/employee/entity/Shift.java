package com.taptrack.employeeservice.employee.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SOLID — Single Responsibility:
 *   Only manages shift timing configuration.
 *   Attendance Service reads shiftStart/shiftEnd via Employee → Shift
 *   instead of storing raw times in Employee.
 *
 * SOLID — Open/Closed:
 *   Adding a new shift type (e.g. ROTATIONAL) only requires inserting
 *   a new row — no code change needed in Employee or Attendance entities.
 *
 * SOLID — Dependency Inversion:
 *   AttendanceService depends on Shift.shiftStart (abstraction),
 *   not on hardcoded "09:00" strings.
 */
@Entity
@Table(
        name = "shifts",
        schema = "employee_schema",
        indexes = {
                @Index(name = "idx_shift_code",   columnList = "shift_code"),
                @Index(name = "idx_shift_active", columnList = "is_active")
        }
)
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shift_code", unique = true, nullable = false, length = 10)
    private String shiftCode;

    @Column(name = "shift_name", nullable = false, length = 50)
    private String shiftName;

    @Column(name = "shift_start", nullable = false)
    private LocalTime shiftStart;

    @Column(name = "shift_end", nullable = false)
    private LocalTime shiftEnd;

    @Column(name = "grace_minutes", nullable = false)
    private Integer graceMinutes = 15;

    @Column(name = "working_hours", precision = 4, scale = 2)
    private BigDecimal workingHours = new BigDecimal("9.00");

    @Column(name = "is_night_shift")
    private Boolean isNightShift = false;

    @Column(name = "description", length = 200)
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
     * ONE Shift → MANY Employees
     * Updating shift timing here affects ALL assigned employees instantly.
     */
    @OneToMany(mappedBy = "shift", fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    // ─── Business Methods ─────────────────────────────────────────

    /**
     * Returns the latest allowed tap-in time before being marked late.
     * Example: shift starts at 09:00 with 15 min grace → 09:15
     */
    public LocalTime getLateThreshold() {
        return shiftStart.plusMinutes(graceMinutes);
    }

    /**
     * Returns overtime threshold — the time after which overtime begins.
     * Example: shift ends at 18:00 → overtime after 18:00
     */
    public LocalTime getOvertimeThreshold() {
        return shiftEnd;
    }

    /**
     * Check if a given tap time is LATE for this shift
     */
    public boolean isLate(LocalTime tapTime) {
        return tapTime.isAfter(getLateThreshold());
    }

    public Shift() {
    }

    public Shift(Long id, String shiftCode, String shiftName, LocalTime shiftStart, LocalTime shiftEnd, Integer graceMinutes, BigDecimal workingHours, Boolean isNightShift, String description, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, List<Employee> employees) {
        this.id = id;
        this.shiftCode = shiftCode;
        this.shiftName = shiftName;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.graceMinutes = graceMinutes;
        this.workingHours = workingHours;
        this.isNightShift = isNightShift;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public Integer getGraceMinutes() {
        return graceMinutes;
    }

    public void setGraceMinutes(Integer graceMinutes) {
        this.graceMinutes = graceMinutes;
    }

    public BigDecimal getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(BigDecimal workingHours) {
        this.workingHours = workingHours;
    }

    public Boolean getNightShift() {
        return isNightShift;
    }

    public void setNightShift(Boolean nightShift) {
        isNightShift = nightShift;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shift shift = (Shift) o;
        return Objects.equals(id, shift.id) && Objects.equals(shiftCode, shift.shiftCode) && Objects.equals(shiftName, shift.shiftName) && Objects.equals(shiftStart, shift.shiftStart) && Objects.equals(shiftEnd, shift.shiftEnd) && Objects.equals(graceMinutes, shift.graceMinutes) && Objects.equals(workingHours, shift.workingHours) && Objects.equals(isNightShift, shift.isNightShift) && Objects.equals(description, shift.description) && Objects.equals(isActive, shift.isActive) && Objects.equals(createdAt, shift.createdAt) && Objects.equals(updatedAt, shift.updatedAt) && Objects.equals(employees, shift.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shiftCode, shiftName, shiftStart, shiftEnd, graceMinutes, workingHours, isNightShift, description, isActive, createdAt, updatedAt, employees);
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", shiftCode='" + shiftCode + '\'' +
                ", shiftName='" + shiftName + '\'' +
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                ", graceMinutes=" + graceMinutes +
                ", workingHours=" + workingHours +
                ", isNightShift=" + isNightShift +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", employees=" + employees +
                '}';
    }
}

