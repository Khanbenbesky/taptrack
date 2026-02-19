package com.taptrack.employeeservice.employee.entity;

/**
 * SOLID — Interface Segregation Principle:
 *   Only entities that support soft-delete implement this interface.
 *   Not every entity needs isActive — only ones that can be deactivated.
 *
 * Implemented by: Employee, Department, Designation, Shift
 * NOT implemented by: EmployeeContact, EmployeeDocument (always active)
 *
 * Usage in Repository:
 *   List<Employee> findByIsActiveTrue();
 *   List<Department> findByIsActiveTrue();
 */
public interface Activatable {

    Boolean getIsActive();

    void setIsActive(Boolean isActive);

    /**
     * Activate the entity
     */
    default void activate() {
        setIsActive(true);
    }

    /**
     * Soft delete — deactivate instead of hard delete
     */
    default void deactivate() {
        setIsActive(false);
    }

    /**
     * Check if this entity is currently active
     */
    default boolean isActive() {
        return Boolean.TRUE.equals(getIsActive());
    }
}
