package com.taptrack.employeeservice.employee.repository;

import com.taptrack.employeeservice.employee.common.ContactType;
import com.taptrack.employeeservice.employee.entity.EmployeeContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeContactRepository extends JpaRepository<EmployeeContact, Long> {

    List<EmployeeContact> findByEmployeeEmployeeId(Long employeeId);
    List<EmployeeContact> findByEmployeeEmployeeIdAndContactType(
            String employeeId, ContactType contactType);
    Optional<EmployeeContact> findByEmployeeEmployeeIdAndIsPrimaryTrue(Long employeeId);
}
