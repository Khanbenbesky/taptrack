package com.taptrack.employeeservice.employee.repository;

import com.taptrack.employeeservice.employee.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentCode(String code);

    List<Department> findByIsActiveTrue();

    boolean existsByDepartmentCode(String code);

    // Fetch department WITH its employees in one query (avoids N+1)
    @Query("SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.employees e " +
            "WHERE d.isActive = true AND (e IS NULL OR e.isActive = true)")
    List<Department> findAllActiveWithEmployees();
}
