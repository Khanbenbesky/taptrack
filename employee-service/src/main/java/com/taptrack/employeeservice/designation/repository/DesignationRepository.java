package com.taptrack.employeeservice.designation.repository;

import com.taptrack.employeeservice.designation.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    Optional<Designation> findByDesignationCodeAndIsDeleteFalse(String designationCode);
    List<Designation> findByIsActiveTrueAndIsDeleteFalse();
    List<Designation> findByDepartmentIdAndIsActiveTrueAndIsDeleteFalse(Long departmentId);
    boolean existsByDesignationCodeAndIsDeleteFalse(String designationCode);
}
