package com.taptrack.employeeservice.employee.repository;

import com.taptrack.employeeservice.employee.common.ContactType;
import com.taptrack.employeeservice.employee.entity.EmployeeContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeContactRepository extends JpaRepository<EmployeeContact, Long> {

    Page<EmployeeContact> findByEmployeeId(Long employeeId, Pageable pageable);

    Optional<EmployeeContact> findByIdAndEmployeeId(Long contactId, Long employeeId);

    Optional<EmployeeContact> findByEmployeeIdAndIsPrimaryTrue(Long employeeId);
}
