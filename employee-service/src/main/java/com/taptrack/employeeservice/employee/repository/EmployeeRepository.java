package com.taptrack.employeeservice.employee.repository;

import com.taptrack.employeeservice.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ─── Existence checks (used for uniqueness validation) ────────

    boolean existsByEmployeeId(String employeeId);

    boolean existsByEmail(String email);

    // Used in update to check uniqueness excluding self
    boolean existsByEmailAndIdNot(String email, Long id);

    // ─── Lookup by business key ───────────────────────────────────

    Optional<Employee> findByEmployeeId(String employeeId);

    // ─── Filter queries ───────────────────────────────────────────

    Page<Employee> findByIsActiveTrue(Pageable pageable);

    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    List<Employee> findByManagerId(Long managerId);

    // ─── Dynamic search query ─────────────────────────────────────
    /**
     * All parameters are optional (null = skip that filter).
     * keyword searches across firstName, lastName, email, and employeeId.
     *
     * Uses LEFT JOIN FETCH to avoid N+1 on department/designation/shift.
     */
    @Query(countName = """
            SELECT e FROM Employee e
            LEFT JOIN FETCH e.department d
            LEFT JOIN FETCH e.designation des
            LEFT JOIN FETCH e.shift s
            WHERE (:departmentId IS NULL OR d.id = :departmentId)
              AND (:designationId IS NULL OR des.id = :designationId)
              AND (:shiftId IS NULL OR s.id = :shiftId)
              AND (:isActive IS NULL OR e.isActive = :isActive)
              AND (:keyword IS NULL OR
                   LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                   LOWER(e.lastName)  LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                   LOWER(e.email)     LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                   LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """,
            countQuery = """
            SELECT COUNT(e) FROM Employee e
            LEFT JOIN e.department d
            LEFT JOIN e.designation des
            LEFT JOIN e.shift s
            WHERE (:departmentId IS NULL OR d.id = :departmentId)
              AND (:designationId IS NULL OR des.id = :designationId)
              AND (:shiftId IS NULL OR s.id = :shiftId)
              AND (:isActive IS NULL OR e.isActive = :isActive)
              AND (:keyword IS NULL OR
                   LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                   LOWER(e.lastName)  LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                   LOWER(e.email)     LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                   LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :keyword, '%')))
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
