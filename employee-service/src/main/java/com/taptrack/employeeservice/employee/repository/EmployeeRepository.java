package com.taptrack.employeeservice.employee.repository;

import com.taptrack.employeeservice.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeId(String employeeId);
    Optional<Employee> findByEmail(String email);

    boolean existsByEmployeeId(String employeeId);
    boolean existsByEmail(String email);
    List<Employee> findByIsActiveTrue();
    List<Employee> findByDepartmentIdAndIsActiveTrue(Long departmentId);
    List<Employee> findByShiftIdAndIsActiveTrue(Long shiftId);
    // Manager's direct reports
    List<Employee> findByManagerEmployeeIdAndIsActiveTrue(String managerEmployeeId);

    // Search employees — used in HR dashboard
    @Query("SELECT e FROM Employee e " +
            "JOIN FETCH e.department d " +
            "JOIN FETCH e.designation des " +
            "JOIN FETCH e.shift s " +
            "WHERE e.isActive = true AND (" +
            "  LOWER(e.firstName)  LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "  LOWER(e.lastName)   LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "  LOWER(e.email)      LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "  e.employeeId        LIKE CONCAT('%', :keyword, '%')" +
            ")")
    List<Employee> searchEmployees(String keyword);

    // Card validation query — called on every machine tap
    // Fetches employee WITH shift details in ONE query (JOIN FETCH)
    @Query("SELECT e FROM Employee e " +
            "JOIN FETCH e.shift s " +
            "JOIN FETCH e.department d " +
            "WHERE e.employeeId = :employeeId AND e.isActive = true")
    Optional<Employee> findActiveEmployeeWithShift(String employeeId);
}
