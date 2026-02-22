package com.taptrack.employeeservice.employee.repository;

import com.taptrack.employeeservice.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmployeeId(String employeeId);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Employee> findByEmployeeId(String employeeId);

    Page<Employee> findByIsActiveTrue(Pageable pageable);
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);
    List<Employee> findByManagerId(Long managerId);

    // ─── Fix: Remove JOIN FETCH — use separate @Query without fetch joins ──
    @Query("""
            SELECT e FROM Employee e
            WHERE (:departmentId IS NULL OR e.department.id = :departmentId)
              AND (:designationId IS NULL OR e.designation.id = :designationId)
              AND (:shiftId IS NULL OR e.shift.id = :shiftId)
              AND (:isActive IS NULL OR e.isActive = :isActive)
              AND (:keyword IS NULL
                   OR LOWER(e.firstName)  LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(e.lastName)   LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(e.email)      LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<Employee> searchEmployees(
            @Param("departmentId") Long departmentId,
            @Param("designationId") Long designationId,
            @Param("shiftId") Long shiftId,
            @Param("isActive") Boolean isActive,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
